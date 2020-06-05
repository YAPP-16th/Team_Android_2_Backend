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
        WHERE mr.match.host.id = :clubId
        AND mr.matchRequestStatus = com.teamplay.domain.database.match.entity.MatchRequestStatus.WAITING
        AND mr.match.startTime > CURRENT_DATE
    """)
    fun findAllHostMatch(clubId: Long): MutableList<MatchRequest>

    @Query("""
        SELECT mr
        from MatchRequest mr
        WHERE mr.requester.id = :clubId
        AND mr.match.startTime > CURRENT_DATE
        AND mr.matchRequestStatus is not com.teamplay.domain.database.match.entity.MatchRequestStatus.ACCEPT
    """)
    fun findAllGuestMatch(clubId: Long): MutableList<MatchRequest>

    @Query("""
        SELECT CASE WHEN COUNT(mr) > 0 THEN true ELSE false END 
        FROM MatchRequest mr
        WHERE mr.match.id = :matchId
        AND mr.requester.id = :requesterClubId
        AND mr.matchRequestStatus is not com.teamplay.domain.database.match.entity.MatchRequestStatus.REJECT
    """)
    fun checkDuplicateRequestMatch(matchId: Long, requesterClubId: Long): Boolean

    @Query("""
        SELECT mr.match.id
        FROM MatchRequest mr
        WHERE mr.id = :matchRequestId
    """)
    fun getMatchIdByMatchRequestId(matchRequestId: Long): Long

    @Query("""
        SELECT CASE WHEN COUNT(mr) > 0 THEN true ELSE false END 
        FROM MatchRequest mr
        WHERE mr.id = :matchRequestId
        AND mr.matchRequestStatus = com.teamplay.domain.database.match.entity.MatchRequestStatus.WAITING
    """)
    fun checkIsWaitingMatchRequestById(matchRequestId: Long): Boolean
}
