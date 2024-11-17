package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.controller.dto.request.ExpenseRequestDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.exception.*
import org.team_leg3nd.tarae.repository.ExpenseRepository
import org.team_leg3nd.tarae.repository.MemberRepository

@Service
class ExpenseService(
    private val expenseRepository: ExpenseRepository,
    private val memberRepository: MemberRepository
) {

    // 지출 생성
    fun createExpense(expenseRequestDto: ExpenseRequestDto): Expense {
        val paidByMembers = expenseRequestDto.paidBy.map { memberRepository.findById(it).orElseThrow { MemberNotFoundException("Member not found") } }
        val sharedWithMembers = expenseRequestDto.sharedWith?.map { memberRepository.findById(it).orElseThrow { MemberNotFoundException("Member not found") } }

        val expense = Expense(description = expenseRequestDto.description, amount = expenseRequestDto.amount, paidBy = paidByMembers, sharedWith = sharedWithMembers)
        return expenseRepository.save(expense)
    }

    // 지출 조회
    fun getExpense(id: String): Expense {
        return expenseRepository.findById(id).orElseThrow { ExpenseNotFoundException("Expense not found") }
    }

    // 지출 수정
    fun updateExpense(id: String, expenseRequestDto: ExpenseRequestDto): Expense {
        val existingExpense = expenseRepository.findById(id).orElseThrow { ExpenseNotFoundException("Expense not found") }
        val paidByMembers = expenseRequestDto.paidBy.map { memberRepository.findById(it).orElseThrow { MemberNotFoundException("Member not found") } }
        val sharedWithMembers = expenseRequestDto.sharedWith?.map { memberRepository.findById(it).orElseThrow { MemberNotFoundException("Member not found") } }

        val updatedExpense = existingExpense.copy(description = expenseRequestDto.description, amount = expenseRequestDto.amount, paidBy = paidByMembers, sharedWith = sharedWithMembers)
        return expenseRepository.save(updatedExpense)
    }

    // 지출 삭제
    fun deleteExpense(id: String) {
        val expense = expenseRepository.findById(id).orElseThrow { ExpenseNotFoundException("Expense not found") }
        expenseRepository.delete(expense)
    }

    // 전체 지출 조회
    fun getAllExpenses(): List<Expense> {
        return expenseRepository.findAll()
    }

    // Expense.paidBy로 지출 조회
    fun getExpenseByPaidBy(paidBy: String): List<Expense> {
        return expenseRepository.findByPaidBy(paidBy)
    }
}


