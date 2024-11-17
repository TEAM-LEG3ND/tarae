package org.team_leg3nd.tarae.controller.dto.response

data class GroupResponseDto(
    val id: String,
    val name: String,
    val members: List<String>? = null,
    val expenses: List<String>? = null
)
