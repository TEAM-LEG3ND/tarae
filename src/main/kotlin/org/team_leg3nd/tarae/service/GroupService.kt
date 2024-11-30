package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.entity.Group
import org.team_leg3nd.tarae.repository.GroupRepository
import org.team_leg3nd.tarae.exception.*

@Service
class GroupService(
    private val groupRepository: GroupRepository,
) {

    // 그룹 생성
    fun createGroup(group: Group): Group {
        return groupRepository.save(group)
    }

    // 그룹 조회
    fun getGroup(id: String): Group {
        return groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }
    }

    // 그룹 수정
    fun updateGroup(id: String, group: Group): Group {
        val existingGroup = groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }

        val updatedGroup = existingGroup.copy(name = group.name, members = group.members, expenses = group.expenses)
        return groupRepository.save(updatedGroup)
    }

    // 그룹 삭제
    fun deleteGroup(id: String) {
        val group = groupRepository.findById(id).orElseThrow { GroupNotFoundException("Group not found") }
        groupRepository.delete(group)
    }

    // 전체 그룹 조회
    fun getAllGroups(): List<Group> {
        return groupRepository.findAll()
    }

    // Group.name으로 그룹 조회
    fun getGroupByName(name: String): Group? {
        return groupRepository.findByName(name)
    }
}

