package com.teamplay.domain.database.jpa.match.repository

import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MatchRepository : JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
    fun getByMatchRequests(matchRequestId: Long): Match

    @Query("""
        SELECT m
        FROM Match m 
        WHERE m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.CLOSE
        AND m.home.id = :homeClubId
        AND m.startTime BETWEEN :startDate AND :endDate
        ORDER BY m.startTime DESC
    """)
    fun findAllAcceptMatchByBetweenDate(homeClubId: Long, startDate: Date, endDate: Date): MutableList<Match>

    @Query("""
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END 
        FROM Match m
        WHERE m.id = :matchId
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.WAITING
    """)
    fun checkIsWaitingMatchById(matchId: Long): Boolean
}
