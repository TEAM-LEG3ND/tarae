package org.team_leg3nd.tarae.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.GroupRequestDto
import org.team_leg3nd.tarae.controller.dto.response.GroupResponseDto
import org.team_leg3nd.tarae.entity.Group
import org.team_leg3nd.tarae.service.GroupService

@RestController
@RequestMapping("/api/groups")
class GroupController(private val groupService: GroupService) {

    // 그룹 생성
    @PostMapping
    fun createGroup(@RequestBody groupRequestDto: GroupRequestDto): ResponseEntity<GroupResponseDto> {
        val group = groupService.createGroup(groupRequestDto)
        return ResponseEntity.ok(group.toResponseDto())
    }

    // 그룹 조회
    @GetMapping("/id")
    fun getGroup(@PathVariable id: String): ResponseEntity<GroupResponseDto> {
        val group = groupService.getGroup(id)
        return ResponseEntity.ok(group.toResponseDto())
    }

    // 그룹 수정
    @PutMapping("/id")
    fun updateGroup(@PathVariable id: String, @RequestBody groupRequestDto: GroupRequestDto): ResponseEntity<GroupResponseDto> {
        val updatedGroup = groupService.updateGroup(id, groupRequestDto)
        return ResponseEntity.ok(updatedGroup.toResponseDto())
    }

    // 그룹 삭제
    @DeleteMapping("/id")
    fun deleteGroup(@PathVariable id: String): ResponseEntity<Void> {
        groupService.deleteGroup(id)
        return ResponseEntity.noContent().build()
    }

    // 전체 그룹 조회
    @GetMapping
    fun getAllGroups(): List<Group> {
        return groupService.getAllGroups()
    }

    // Group.name으로 그룹 조회
    @GetMapping("/name")
    fun getGroupByName(@RequestParam name: String): Group? {
        return groupService.getGroupByName(name)
    }

    fun Group.toResponseDto(): GroupResponseDto {
        val memberNames = this.members?.map { it.name }
        val expenseIds = this.expenses?.map { it.id!! }
        return GroupResponseDto(id = this.id ?: "", name = this.name, members = memberNames, expenses = expenseIds)
    }
}



