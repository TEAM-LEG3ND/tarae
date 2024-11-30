package org.team_leg3nd.tarae.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.ExpenseRequestDto
import org.team_leg3nd.tarae.controller.dto.response.ExpenseResponseDto
import org.team_leg3nd.tarae.controller.dto.response.MemberProjectionResponseDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.service.ExpenseService
import org.team_leg3nd.tarae.service.MemberService

@RestController
@RequestMapping("/api/expenses")
class ExpenseController(
    private val expenseService: ExpenseService,
    private val memberService: MemberService
) {

    @Operation(summary = "create expense", description = "create expense")
    @PostMapping
    fun createExpense(@RequestBody expenseRequestDto: ExpenseRequestDto): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.createExpense(expenseRequestDto.toExpense())

        return ResponseEntity.ok(expense.toResponseDto())
    }

    @Operation(summary = "read expense", description = "read expense")
    @GetMapping("/id/{id}")
    fun getExpense(@PathVariable id: String): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.getExpense(id)

        return ResponseEntity.ok(expense.toResponseDto())
    }

    @Operation(summary = "update expense", description = "It is updated with values sent to the request body")
    @PutMapping("/id/{id}")
    fun updateExpense(
        @PathVariable id: String,
        @RequestBody expenseRequestDto: ExpenseRequestDto
    ): ResponseEntity<ExpenseResponseDto> {
        val updatedExpense = expenseService.updateExpense(id, expenseRequestDto.toExpense())

        return ResponseEntity.ok(updatedExpense.toResponseDto())
    }

    @Operation(summary = "delete expense", description = "delete expense")
    @DeleteMapping("/id/{id}")
    fun deleteExpense(@PathVariable id: String): ResponseEntity<Void> {
        expenseService.deleteExpense(id)

        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "read all expense", description = "read all expense")
    @GetMapping
    fun getAllExpenses(): ResponseEntity<List<ExpenseResponseDto>> {
        val allExpenses = expenseService.getAllExpenses()

        return ResponseEntity.ok(allExpenses.map { expense: Expense -> expense.toResponseDto() })
    }

    fun Expense.toResponseDto(): ExpenseResponseDto {
        return ExpenseResponseDto(
            id = this.id ?: "",
            description = this.description,
            amount = this.amount,
            paidBy = this.paidBy.map { member: Member ->
                MemberProjectionResponseDto(
                    id = member.id!!,
                    name = member.name
                )
            },
            sharedWith = this.sharedWith?.map { member: Member ->
                MemberProjectionResponseDto(
                    id = member.id!!,
                    name = member.name
                )
            }
        )
    }

    fun ExpenseRequestDto.toExpense(): Expense {
        return Expense(
            description = this.description,
            amount = this.amount,
            paidBy = this.paidBy.map { id: String ->
                memberService.getMember(id)
            },
            sharedWith = this.sharedWith?.map { id: String ->
                memberService.getMember(id)
            }
        )
    }
}


