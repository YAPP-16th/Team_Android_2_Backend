package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface ClubRepository : ExtendedRepository<Club> {
    fun existsByName(name: String): Boolean

    fun findAllByNameContaining(name: String, pageable: Pageable): Page<Club>
}