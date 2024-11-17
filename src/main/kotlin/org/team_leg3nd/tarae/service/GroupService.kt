package org.team_leg3nd.tarae.service

import org.springframework.stereotype.Service
import org.team_leg3nd.tarae.entity.Group
import org.team_leg3nd.tarae.repository.GroupRepository

@Service
class GroupService(private val groupRepository: GroupRepository) {

    fun createGroup(group: Group) = groupRepository.save(group)

    fun getGroupById(id: String) = groupRepository.findById(id).orElse(null)

    fun updateGroup(id: String, group: Group) = groupRepository.save(group.copy(id = id))

    fun deleteGroup(id: String) = groupRepository.deleteById(id)

    fun getAllGroups() = groupRepository.findAll()
}
