package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.controller.dto.request.GroupRequestDto
import org.team_leg3nd.tarae.entity.Group
import org.team_leg3nd.tarae.repository.ExpenseRepository
import org.team_leg3nd.tarae.repository.GroupRepository
import org.team_leg3nd.tarae.repository.MemberRepository
import org.team_leg3nd.tarae.exception.*

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val memberRepository: MemberRepository,
    private val expenseRepository: ExpenseRepository
) {

    // 그룹 생성
    fun createGroup(groupRequestDto: GroupRequestDto): Group {
        val members = groupRequestDto.members?.let { memberRepository.findAllById(it) } ?: emptyList()
        val expenses = groupRequestDto.expenses?.let { expenseRepository.findAllById(it) } ?: emptyList()

        val group = Group(name = groupRequestDto.name, members = members, expenses = expenses)
        return groupRepository.save(group)
    }

    // 그룹 조회
    fun getGroup(id: String): Group {
        return groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }
    }

    // 그룹 수정
    fun updateGroup(id: String, groupRequestDto: GroupRequestDto): Group {
        val existingGroup = groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }
        val members = groupRequestDto.members?.let { memberRepository.findAllById(it) } ?: existingGroup.members
        val expenses = groupRequestDto.expenses?.let { expenseRepository.findAllById(it) } ?: existingGroup.expenses

        val updatedGroup = existingGroup.copy(name = groupRequestDto.name, members = members, expenses = expenses)
        return groupRepository.save(updatedGroup)
    }

    // 그룹 삭제
    fun deleteGroup(id: String) {
        val group = groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }
        groupRepository.delete(group)
    }
}

