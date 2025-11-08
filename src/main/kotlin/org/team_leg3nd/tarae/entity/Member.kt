package org.team_leg3nd.tarae.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "members")
data class Member(
    @Id val id: String? = null,
    val name: String,
    val paidExpenses: List<Expense>? = null
)