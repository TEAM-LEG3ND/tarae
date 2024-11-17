package org.team_leg3nd.tarae.controller.dto.request

data class GroupRequestDto(
    val name: String,
    val members: List<String>? = null,  // Member ID 리스트
    val expenses: List<String>? = null  // Expense ID 리스트
)


