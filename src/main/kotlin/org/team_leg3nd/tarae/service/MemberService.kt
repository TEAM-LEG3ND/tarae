package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.controller.dto.request.MemberRequestDto
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.repository.MemberRepository
import org.team_leg3nd.tarae.exception.*
import org.team_leg3nd.tarae.repository.ExpenseRepository

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val expenseRepository: ExpenseRepository
) {

    // 멤버 생성
    fun createMember(memberRequestDto: MemberRequestDto): Member {
        val paidExpenses = memberRequestDto.paidExpenses?.let { expenseRepository.findAllById(it) } ?: emptyList()
        val member = Member(name = memberRequestDto.name, paidExpenses = paidExpenses)
        return memberRepository.save(member)
    }

    // 멤버 조회
    fun getMember(id: String): Member {
        return memberRepository.findById(id).orElseThrow { MemberNotFoundException("Member not found") }
    }

    // 멤버 수정
    fun updateMember(id: String, memberRequestDto: MemberRequestDto): Member {
        val existingMember = memberRepository.findById(id).orElseThrow { MemberNotFoundException("Member not found") }
        val paidExpenses = memberRequestDto.paidExpenses?.let { expenseRepository.findAllById(it) } ?: existingMember.paidExpenses

        val updatedMember = existingMember.copy(name = memberRequestDto.name, paidExpenses = paidExpenses)
        return memberRepository.save(updatedMember)
    }

    // 멤버 삭제
    fun deleteMember(id: String) {
        val member = memberRepository.findById(id).orElseThrow { MemberNotFoundException("Member not found") }
        memberRepository.delete(member)
    }

    // 전체 멤버 조회
    fun getAllMembers(): List<Member> {
        return memberRepository.findAll()
    }

    // Member.name으로 멤버 조회
    fun getMemberByName(name: String): Member? {
        return memberRepository.findByName(name)
    }
}

