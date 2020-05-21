package com.teamplay.domain.database.jpa.match.repository

import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository : JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
    fun getByMatchRequests(matchRequest: MatchRequest): Match

    @Query("""
        SELECT m 
        FROM Match m 
        WHERE m.matchStatus = com.teamplay.domain.database.match.entity.MatchRequestStatus.ACCEPT
        AND m.home = :homeClubId
        AND m.startTime BETWEEN CURRENT_DATE AND sysdate + 30/(24*60)
        ORDER BY m.startTime DESC
    """)
    fun findAllAcceptMatch(homeClubId: Long): MutableList<Match>

    @Query("""
        SELECT COUNT(m) > 0 
        FROM Match m
        WHERE m.id = :matchId
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.WAITING
    """)
    fun checkIsWaitingMatchById(matchId: Long): Boolean
}
