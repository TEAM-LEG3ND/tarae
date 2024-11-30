package org.team_leg3nd.tarae.controller

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

    // 멤버 생성
    @PostMapping
    fun createMember(@RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val member = memberService.createMember(memberRequestDto.toMember())

        return ResponseEntity.ok(member.toResponseDto())
    }

    // 멤버 조회
    @GetMapping("/id/{id}")
    fun getMember(@PathVariable id: String): ResponseEntity<MemberResponseDto> {
        val member = memberService.getMember(id)

        return ResponseEntity.ok(member.toResponseDto())
    }

    // 멤버 수정
    @PutMapping("/id/{id}")
    fun updateMember(
        @PathVariable id: String,
        @RequestBody memberRequestDto: MemberRequestDto
    ): ResponseEntity<MemberResponseDto> {
        val updatedMember = memberService.updateMember(id, memberRequestDto.toMember())

        return ResponseEntity.ok(updatedMember.toResponseDto())
    }

    // 멤버 삭제
    @DeleteMapping("/id/{id}")
    fun deleteMember(@PathVariable id: String): ResponseEntity<Void> {
        memberService.deleteMember(id)

        return ResponseEntity.noContent().build()
    }

    // 전체 멤버 조회
    @GetMapping
    fun getAllMembers(): ResponseEntity<List<MemberResponseDto>> {
        val allMembers = memberService.getAllMembers()

        return ResponseEntity.ok(allMembers.map { member: Member -> member.toResponseDto() })
    }

    // Member.name으로 멤버 조회
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

