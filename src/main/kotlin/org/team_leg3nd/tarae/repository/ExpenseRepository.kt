package org.team_leg3nd.tarae.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.team_leg3nd.tarae.entity.Expense

@Repository
interface ExpenseRepository : MongoRepository<Expense, String> {
    override fun findAll(): List<Expense>
}