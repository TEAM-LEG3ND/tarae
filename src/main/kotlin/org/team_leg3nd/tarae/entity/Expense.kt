package org.team_leg3nd.tarae.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "expenses")
data class Expense(
    @Id val id: String? = null,
    val description: String,
    val amount: Double,
    val paidBy: Member,
    val sharedWith: List<Member>? = null
)
