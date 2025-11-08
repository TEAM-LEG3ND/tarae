package org.team_leg3nd.tarae.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.team_leg3nd.tarae.controller.dto.request.MemberRequestDto
import org.team_leg3nd.tarae.controller.dto.response.ExpenseProjectionResponseDto
import org.team_leg3nd.tarae.controller.dto.response.MemberResponseDto
import org.team_leg3nd.tarae.entity.Expense
import org.team_leg3nd.tarae.entity.Member
import org.team_leg3nd.tarae.service.ExpenseService
import org.team_leg3nd.tarae.service.MemberService

@Tag(name = "Member API", description = "Operations related to members")
@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberService: MemberService,
    private val expenseService: ExpenseService
) {

    @Operation(
        summary = "create member",
        description = "create member",
        requestBody = RequestBody(
            description = "Member creation details",
            required = true
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no expense", content = [Content(examples = [ExampleObject(name = "ExpenseNotFound", value = "Expense not found", description = "No Expense has a matching ID")])])
        ]
    )
    @PostMapping
    fun createMember(@RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val member = memberService.createMember(memberRequestDto.toMember())

        return ResponseEntity.ok(member.toResponseDto())
    }

    @Operation(
        summary = "read member",
        description = "read member",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no member", content = [Content(examples = [ExampleObject(name = "MemberNotFound", value = "Member not found", description = "No member has a matching ID")])])
        ]
    )
    @GetMapping("/id/{id}")
    fun getMember(@PathVariable id: String): ResponseEntity<MemberResponseDto> {
        val member = memberService.getMember(id)

        return ResponseEntity.ok(member.toResponseDto())
    }

    @Operation(
        summary = "update member",
        description = "It is updated with values sent to the request body",
        requestBody = RequestBody(
            description = "Member updating details",
            required = true
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no expense or member", content = [Content(examples = [ExampleObject(name = "ExpenseNotFound", value = "Expense not found", description = "No Expense has a matching ID"), ExampleObject(name = "MemberNotFound", value = "Member not found", description = "No member has a matching ID")])])
        ]
    )
    @PutMapping("/id/{id}")
    fun updateMember(
        @PathVariable id: String,
        @RequestBody memberRequestDto: MemberRequestDto
    ): ResponseEntity<MemberResponseDto> {
        val updatedMember = memberService.updateMember(id, memberRequestDto.toMember())

        return ResponseEntity.ok(updatedMember.toResponseDto())
    }

    @Operation(
        summary = "delete member",
        description = "delete member",
        responses = [
            ApiResponse(responseCode = "204", description = "Successful operation"),
            ApiResponse(responseCode = "404", description = "There is no member", content = [Content(examples = [ExampleObject(name = "MemberNotFound", value = "Member not found", description = "No member has a matching ID")])])
        ]
    )
    @DeleteMapping("/id/{id}")
    fun deleteMember(@PathVariable id: String): ResponseEntity<Void> {
        memberService.deleteMember(id)

        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "read all member",
        description = "read all member",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
    @GetMapping
    fun getAllMembers(): ResponseEntity<List<MemberResponseDto>> {
        val allMembers = memberService.getAllMembers()

        return ResponseEntity.ok(allMembers.map { member: Member -> member.toResponseDto() })
    }

    @Operation(
        summary = "read member by name",
        description = "read member by name",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful operation")
        ]
    )
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

