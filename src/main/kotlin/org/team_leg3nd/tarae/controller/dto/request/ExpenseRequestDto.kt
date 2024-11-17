package org.team_leg3nd.tarae.controller.dto.request

data class ExpenseRequestDto(
    val description: String,
    val amount: Double,
    val paidBy: List<String>,  // Member ID
    val sharedWith: List<String>? = null  // Member ID List
)
