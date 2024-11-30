package org.team_leg3nd.tarae.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.GroupRequestDto
import org.team_leg3nd.tarae.controller.dto.response.ExpenseProjectionResponseDto
import org.team_leg3nd.tarae.controller.dto.response.GroupResponseDto
import org.team_leg3nd.tarae.controller.dto.response.MemberProjectionResponseDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.entity.Group
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.service.ExpenseService
import org.team_leg3nd.tarae.service.GroupService
import org.team_leg3nd.tarae.service.MemberService

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService: GroupService,
    private val memberService: MemberService,
    private val expenseService: ExpenseService
) {

    // 그룹 생성
    @PostMapping
    fun createGroup(@RequestBody groupRequestDto: GroupRequestDto): ResponseEntity<GroupResponseDto> {
        val group = groupService.createGroup(groupRequestDto.toGroup())

        return ResponseEntity.ok(group.toResponseDto())
    }

    // 그룹 조회
    @GetMapping("/id/{id}")
    fun getGroup(@PathVariable id: String): ResponseEntity<GroupResponseDto> {
        val group = groupService.getGroup(id)

        return ResponseEntity.ok(group.toResponseDto())
    }

    // 그룹 수정
    @PutMapping("/id/{id}")
    fun updateGroup(
        @PathVariable id: String,
        @RequestBody groupRequestDto: GroupRequestDto
    ): ResponseEntity<GroupResponseDto> {
        val updatedGroup = groupService.updateGroup(id, groupRequestDto.toGroup())

        return ResponseEntity.ok(updatedGroup.toResponseDto())
    }

    // 그룹 삭제
    @DeleteMapping("/id/{id}")
    fun deleteGroup(@PathVariable id: String): ResponseEntity<Void> {
        groupService.deleteGroup(id)

        return ResponseEntity.noContent().build()
    }

    // 전체 그룹 조회
    @GetMapping
    fun getAllGroups(): ResponseEntity<List<GroupResponseDto>> {
        val allGroups = groupService.getAllGroups()

        return ResponseEntity.ok(allGroups.map { group: Group -> group.toResponseDto() })
    }

    // Group.name으로 그룹 조회
    @GetMapping("/name/{name}")
    fun getGroupByName(@PathVariable name: String): ResponseEntity<GroupResponseDto> {
        val groupByName = groupService.getGroupByName(name)

        return ResponseEntity.ok(groupByName?.toResponseDto())
    }

    fun Group.toResponseDto(): GroupResponseDto {
        return GroupResponseDto(
            id = this.id ?: "",
            name = this.name,
            members = this.members?.map { member: Member ->
                MemberProjectionResponseDto(
                    id = member.id!!,
                    name = member.name
                )
            },
            expenses = this.expenses?.map { expense: Expense ->
                ExpenseProjectionResponseDto(
                    id = expense.id!!,
                    description = expense.description,
                    amount = expense.amount
                )
            }
        )
    }

    fun GroupRequestDto.toGroup(): Group {
        return Group(
            name = this.name,
            members = this.members?.map { id: String ->
                memberService.getMember(id)
            },
            expenses = this.expenses?.map { id: String ->
                expenseService.getExpense(id)
            }
        )
    }
}



