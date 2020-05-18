package com.teamplay.domain.database.jpa.match.repository

import com.teamplay.domain.database.match.entity.MatchRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MatchRequestRepository : JpaRepository<MatchRequest, Long> {
    @Query("""
        SELECT mr
        from MatchRequest mr
        WHERE mr.match.home = :club
        AND mr.matchRequestStatus = com.teamplay.domain.database.match.entity.MatchRequestStatus.WAITING
        AND mr.match.startTime > CURRENT_DATE
    """)
    fun findAllHostMatch(club: Long): MutableList<MatchRequest>

    @Query("""
        SELECT mr
        from MatchRequest mr
        WHERE mr.requester = :club
        AND mr.match.startTime > CURRENT_DATE
    """)
    fun findAllGuestMatch(club: Long): MutableList<MatchRequest>
}
