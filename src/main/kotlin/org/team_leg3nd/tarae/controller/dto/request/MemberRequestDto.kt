package org.team_leg3nd.tarae.controller.dto.request

data class MemberRequestDto(
    val name: String,
    val paidExpenses: List<String>? = null  // Expense ID 리스트
)
