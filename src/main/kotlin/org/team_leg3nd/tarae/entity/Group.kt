package org.team_leg3nd.tarae.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "groups")
data class Group(
    @Id val id: String? = null,
    val name: String,
    val members: List<Member>? = null,
    val expenses: List<Expense>? = null
)
