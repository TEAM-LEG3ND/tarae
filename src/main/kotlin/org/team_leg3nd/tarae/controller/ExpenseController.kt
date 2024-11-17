package org.team_leg3nd.tarae.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.ExpenseRequestDto
import org.team_leg3nd.tarae.controller.dto.response.ExpenseResponseDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.service.ExpenseService

@RestController
@RequestMapping("/api/expenses")
class ExpenseController(private val expenseService: ExpenseService) {

    @PostMapping
    fun createExpense(@RequestBody expenseRequestDto: ExpenseRequestDto): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.createExpense(expenseRequestDto)
        return ResponseEntity.ok(expense.toResponseDto())
    }

    @GetMapping("/{id}")
    fun getExpense(@PathVariable id: String): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.getExpense(id)
        return ResponseEntity.ok(expense.toResponseDto())
    }

    @PutMapping("/{id}")
    fun updateExpense(@PathVariable id: String, @RequestBody expenseRequestDto: ExpenseRequestDto): ResponseEntity<ExpenseResponseDto> {
        val updatedExpense = expenseService.updateExpense(id, expenseRequestDto)
        return ResponseEntity.ok(updatedExpense.toResponseDto())
    }

    @DeleteMapping("/{id}")
    fun deleteExpense(@PathVariable id: String): ResponseEntity<Void> {
        expenseService.deleteExpense(id)
        return ResponseEntity.noContent().build()
    }

    // 전체 지출 조회
    @GetMapping
    fun getAllExpenses(): List<Expense> {
        return expenseService.getAllExpenses()
    }

    // Expense.paidBy로 지출 조회
    @GetMapping("/paidBy")
    fun getExpenseByPaidBy(@RequestBody paidBy: String): List<Expense> {
        return expenseService.getExpenseByPaidBy(paidBy)
    }

    fun Expense.toResponseDto(): ExpenseResponseDto {
        val paidByNames = this.paidBy.map { it.name }
        val sharedWithNames = this.sharedWith?.map { it.name }
        return ExpenseResponseDto(id = this.id ?: "", description = this.description, amount = this.amount, paidBy = paidByNames, sharedWith = sharedWithNames)
    }
}


