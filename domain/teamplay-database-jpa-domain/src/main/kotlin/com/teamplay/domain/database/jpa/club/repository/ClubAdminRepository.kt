package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.stereotype.Repository

@Repository
interface ClubAdminRepository : ExtendedRepository<ClubAdmin> {
}