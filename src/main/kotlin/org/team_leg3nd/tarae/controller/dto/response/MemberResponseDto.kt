package org.team_leg3nd.tarae.controller.dto.response

data class MemberResponseDto(
    val id: String,
    val name: String,
    val paidExpenses: List<String>? = null
)