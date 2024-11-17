package org.team_leg3nd.tarae.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.team_leg3nd.tarae.entity.Group

@Repository
interface GroupRepository : MongoRepository<Group, String> {
    fun findByName(name: String): Group?

    override fun findAll(): List<Group>
}