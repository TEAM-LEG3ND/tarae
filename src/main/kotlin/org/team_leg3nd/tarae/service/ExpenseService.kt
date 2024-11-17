package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.repository.ExpenseRepository

@Service
class ExpenseService(private val expenseRepository: ExpenseRepository) {

    fun createExpense(expense: Expense) = expenseRepository.save(expense)

    fun getExpenseById(id: String) = expenseRepository.findById(id).orElse(null)

    fun getExpensesByMember(memberId: String) = expenseRepository.findByPaidBy(memberId)

    fun updateExpense(id: String, expense: Expense) = expenseRepository.save(expense.copy(id = id))

    fun deleteExpense(id: String) = expenseRepository.deleteById(id)

    fun getAllExpenses() = expenseRepository.findAll()
}
