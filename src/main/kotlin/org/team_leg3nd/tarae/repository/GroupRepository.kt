package org.team_leg3nd.tarae.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.team_leg3nd.tarae.entity.Group

interface GroupRepository : MongoRepository<Group, String>