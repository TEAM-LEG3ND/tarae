package org.team_leg3nd.tarae.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
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

@Tag(name = "Group API", description = "Operations related to Groups")
@RequestMapping("/api/groups")
@RestController
class GroupController(
    private val groupService: GroupService,
    private val memberService: MemberService,
    private val expenseService: ExpenseService
) {

    @Operation(
        summary = "create group",
        description = "create group",
        requestBody = RequestBody(
            description = "Group creation details",
            required = true
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no expense or member", content = [Content(examples = [ExampleObject(name = "ExpenseNotFound", value = "Expense not found", description = "No Expense has a matching ID"), ExampleObject(name = "MemberNotFound", value = "Member not found", description = "No member has a matching ID")])])
        ]
    )
    @PostMapping
    fun createGroup(@RequestBody groupRequestDto: GroupRequestDto): ResponseEntity<GroupResponseDto> {
        val group = groupService.createGroup(groupRequestDto.toGroup())

        return ResponseEntity.ok(group.toResponseDto())
    }

    @Operation(
        summary = "read group",
        description = "read group",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no group", content = [Content(examples = [ExampleObject(name = "GroupNotFound", value = "Group not found", description = "No Group has a matching ID")])])
        ]
    )
    @GetMapping("/id/{id}")
    fun getGroup(@PathVariable id: String): ResponseEntity<GroupResponseDto> {
        val group = groupService.getGroup(id)

        return ResponseEntity.ok(group.toResponseDto())
    }

    @Operation(
        summary = "update group",
        description = "It is updated with values sent to the request body",
        requestBody = RequestBody(
            description = "Group updating details",
            required = true
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no expense or member", content = [Content(examples = [ExampleObject(name = "ExpenseNotFound", value = "Expense not found", description = "No Expense has a matching ID"), ExampleObject(name = "MemberNotFound", value = "Member not found", description = "No member has a matching ID")])])
        ]
    )
    @PutMapping("/id/{id}")
    fun updateGroup(
        @PathVariable id: String,
        @RequestBody groupRequestDto: GroupRequestDto
    ): ResponseEntity<GroupResponseDto> {
        val updatedGroup = groupService.updateGroup(id, groupRequestDto.toGroup())

        return ResponseEntity.ok(updatedGroup.toResponseDto())
    }

    @Operation(
        summary = "delete group",
        description = "delete group",
        responses = [
            ApiResponse(responseCode = "204", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no group", content = [Content(examples = [ExampleObject(name = "GroupNotFound", value = "Group not found", description = "No Group has a matching ID")])])
        ]
    )
    @DeleteMapping("/id/{id}")
    fun deleteGroup(@PathVariable id: String): ResponseEntity<Void> {
        groupService.deleteGroup(id)

        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "read all group",
        description = "read all group",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    @GetMapping
    fun getAllGroups(): ResponseEntity<List<GroupResponseDto>> {
        val allGroups = groupService.getAllGroups()

        return ResponseEntity.ok(allGroups.map { group: Group -> group.toResponseDto() })
    }

    @Operation(
        summary = "read group by name",
        description = "read group by name",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
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



