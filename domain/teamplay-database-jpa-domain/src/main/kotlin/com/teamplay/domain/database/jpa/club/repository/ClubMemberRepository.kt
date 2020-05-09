package com.teamplay.domain.database.jpa.club.repository

import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.ExtendedRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClubMemberRepository : ExtendedRepository<ClubMember> {
    fun findAllByClubId(clubId: Long): MutableList<ClubMember>
}