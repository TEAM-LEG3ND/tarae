package org.team_leg3nd.tarae.controller.dto.response

data class ExpenseResponseDto(
    val id: String,
    val description: String,
    val amount: Double,
    val paidBy: List<MemberProjectionResponseDto>,  // Member id 리스트
    val sharedWith: List<MemberProjectionResponseDto>? = null
)
