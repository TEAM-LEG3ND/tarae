package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.exception.*
import org.team_leg3nd.tarae.repository.ExpenseRepository

@Service
class ExpenseService(
    private val expenseRepository: ExpenseRepository,
) {

    // 지출 생성
    fun createExpense(expense: Expense): Expense {
        return expenseRepository.save(expense)
    }

    // 지출 조회
    fun getExpense(id: String): Expense {
        return expenseRepository.findById(id).orElseThrow { ExpenseNotFoundException("Expense not found") }
    }

    // 지출 수정
    fun updateExpense(id: String, expense: Expense): Expense {
        val existingExpense = expenseRepository.findById(id).orElseThrow { ExpenseNotFoundException("Expense not found") }

        val updatedExpense = existingExpense.copy(description = expense.description, amount = expense.amount, paidBy = expense.paidBy, sharedWith = expense.sharedWith)
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
}


