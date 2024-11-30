package org.team_leg3nd.tarae.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.MemberRequestDto
import org.team_leg3nd.tarae.controller.dto.response.ExpenseProjectionResponseDto
import org.team_leg3nd.tarae.controller.dto.response.MemberResponseDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.service.ExpenseService
import org.team_leg3nd.tarae.service.MemberService

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberService: MemberService,
    private val expenseService: ExpenseService
) {

    @Operation(summary = "create member", description = "create member")
    @PostMapping
    fun createMember(@RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val member = memberService.createMember(memberRequestDto.toMember())

        return ResponseEntity.ok(member.toResponseDto())
    }

    @Operation(summary = "read member", description = "read member")
    @GetMapping("/id/{id}")
    fun getMember(@PathVariable id: String): ResponseEntity<MemberResponseDto> {
        val member = memberService.getMember(id)

        return ResponseEntity.ok(member.toResponseDto())
    }

    @Operation(summary = "update member", description = "It is updated with values sent to the request body")
    @PutMapping("/id/{id}")
    fun updateMember(
        @PathVariable id: String,
        @RequestBody memberRequestDto: MemberRequestDto
    ): ResponseEntity<MemberResponseDto> {
        val updatedMember = memberService.updateMember(id, memberRequestDto.toMember())

        return ResponseEntity.ok(updatedMember.toResponseDto())
    }

    @Operation(summary = "delete member", description = "delete member")
    @DeleteMapping("/id/{id}")
    fun deleteMember(@PathVariable id: String): ResponseEntity<Void> {
        memberService.deleteMember(id)

        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "read all member", description = "read all member")
    @GetMapping
    fun getAllMembers(): ResponseEntity<List<MemberResponseDto>> {
        val allMembers = memberService.getAllMembers()

        return ResponseEntity.ok(allMembers.map { member: Member -> member.toResponseDto() })
    }

    @Operation(summary = "read member by name", description = "read member by name")
    @GetMapping("/name/{name}")
    fun getMemberByName(@PathVariable name: String): ResponseEntity<MemberResponseDto> {
        val memberByName = memberService.getMemberByName(name)

        return ResponseEntity.ok(memberByName?.toResponseDto())
    }

    fun Member.toResponseDto(): MemberResponseDto {
        return MemberResponseDto(
            id = this.id ?: "",
            name = this.name,
            paidExpenses = this.paidExpenses?.map { expense: Expense ->
                ExpenseProjectionResponseDto(
                    id = expense.id!!,
                    description = expense.description,
                    amount = expense.amount
                )
            }
        )
    }

    fun MemberRequestDto.toMember(): Member {
        return Member(
            name = this.name,
            paidExpenses = this.paidExpenses?.map { id: String ->
                expenseService.getExpense(id)
            }
        )
    }
}

