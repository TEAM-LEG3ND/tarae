package org.team_leg3nd.tarae.controller

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

    @PostMapping
    fun createExpense(@RequestBody expenseRequestDto: ExpenseRequestDto): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.createExpense(expenseRequestDto.toExpense())

        return ResponseEntity.ok(expense.toResponseDto())
    }

    @GetMapping("/id/{id}")
    fun getExpense(@PathVariable id: String): ResponseEntity<ExpenseResponseDto> {
        val expense = expenseService.getExpense(id)

        return ResponseEntity.ok(expense.toResponseDto())
    }

    @PutMapping("/id/{id}")
    fun updateExpense(
        @PathVariable id: String,
        @RequestBody expenseRequestDto: ExpenseRequestDto
    ): ResponseEntity<ExpenseResponseDto> {
        val updatedExpense = expenseService.updateExpense(id, expenseRequestDto.toExpense())

        return ResponseEntity.ok(updatedExpense.toResponseDto())
    }

    @DeleteMapping("/id/{id}")
    fun deleteExpense(@PathVariable id: String): ResponseEntity<Void> {
        expenseService.deleteExpense(id)

        return ResponseEntity.noContent().build()
    }

    // 전체 지출 조회
    @GetMapping
    fun getAllExpenses(): ResponseEntity<List<ExpenseResponseDto>> {
        val allExpenses = expenseService.getAllExpenses()

        return ResponseEntity.ok(allExpenses.map { expense: Expense -> expense.toResponseDto() })
    }

    // Expense.paidBy로 지출 조회
    @GetMapping("/paidBy/{paidBy}")
    fun getExpenseByPaidBy(@PathVariable paidBy: String): ResponseEntity<List<ExpenseResponseDto>> {
        val expenseByPaidBy = expenseService.getExpenseByPaidBy(paidBy)

        return ResponseEntity.ok(expenseByPaidBy.map { expense: Expense -> expense.toResponseDto() })
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


