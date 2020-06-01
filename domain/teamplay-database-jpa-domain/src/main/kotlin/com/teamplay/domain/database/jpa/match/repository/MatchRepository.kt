package com.teamplay.domain.database.jpa.match.repository

import com.teamplay.domain.database.match.entity.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MatchRepository : JpaRepository<Match, Long>, JpaSpecificationExecutor<Match> {
    fun getByMatchRequests_Id(matchRequestId: Long): Match

    @Query("""
        SELECT m
        FROM Match m 
        WHERE m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.CLOSE
        AND m.startTime BETWEEN :startDate AND :endDate
        AND (m.host.id = :clubId OR m.guest.id = :clubId)
        ORDER BY m.startTime DESC
    """)
    fun findAllAcceptMatchByBetweenDate(clubId: Long, startDate: Date, endDate: Date): MutableList<Match>

    @Query("""
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END 
        FROM Match m
        WHERE m.id = :matchId
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.WAITING
    """)
    fun checkIsWaitingMatchById(matchId: Long): Boolean

    @Query("""
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END 
        FROM Match m
        WHERE m.id = :matchId
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.CLOSE
        AND m.endTime < current_time
    """)
    fun checkIsCloseMatchById(matchId: Long): Boolean

    @Query("""
        SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END 
        FROM Match m
        WHERE m.id = :matchId
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.END
        AND m.endTime < current_time
    """)
    fun checkIsEndMatchById(matchId: Long): Boolean

    @Query("""
        SELECT m
        FROM Match m
        WHERE (m.host.id = :clubId OR m.guest.id = :clubId)
        AND m.matchStatus = com.teamplay.domain.database.match.entity.MatchStatus.END
        ORDER BY m.endTime
    """)
    fun getRecentlyRecordById(clubId: Long): MutableList<Match>
}
