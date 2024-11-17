package org.team_leg3nd.tarae.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.team_leg3nd.tarae.entity.Member


@Repository
interface MemberRepository : MongoRepository<Member, String> {
    fun findByName(name: String): List<Member>
}