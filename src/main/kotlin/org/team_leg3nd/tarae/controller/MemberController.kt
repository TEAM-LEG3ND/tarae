package org.team_leg3nd.tarae.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.MemberRequestDto
import org.team_leg3nd.tarae.controller.dto.response.MemberResponseDto
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.service.MemberService

@RestController
@RequestMapping("/api/members")
class MemberController(private val memberService: MemberService) {

    // 멤버 생성
    @PostMapping
    fun createMember(@RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val member = memberService.createMember(memberRequestDto)
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
    fun updateMember(@PathVariable id: String, @RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val updatedMember = memberService.updateMember(id, memberRequestDto)
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
    fun getAllMembers(): List<Member> {
        return memberService.getAllMembers()
    }

    // Member.name으로 멤버 조회
    @GetMapping("/name/{name}")
    fun getMemberByName(@PathVariable name: String): Member? {
        return memberService.getMemberByName(name)
    }

    fun Member.toResponseDto(): MemberResponseDto {
        val paidExpenseIds = this.paidExpenses?.map { it.id!! }
        return MemberResponseDto(id = this.id ?: "", name = this.name, paidExpenses = paidExpenseIds)
    }
}

