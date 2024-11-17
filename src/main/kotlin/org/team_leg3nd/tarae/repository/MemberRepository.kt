package org.team_leg3nd.tarae.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.team_leg3nd.tarae.entity.Member


interface MemberRepository : MongoRepository<Member, String>