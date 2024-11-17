package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.repository.MemberRepository

@Service
class MemberService(private val memberRepository: MemberRepository) {

    fun createMember(member: Member) = memberRepository.save(member)

    fun getMemberById(id: String) = memberRepository.findById(id).orElse(null)

    fun getMembersByName(name: String) = memberRepository.findByName(name)

    fun updateMember(id: String, member: Member) = memberRepository.save(member.copy(id = id))

    fun deleteMember(id: String) = memberRepository.deleteById(id)

    fun getAllMembers() = memberRepository.findAll()
}
