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
        from Match m 
        WHERE m.matchStatus = com.teamplay.domain.database.match.entity.MatchRequestStatus.ACCEPT
        AND m.home = :homeClub
        AND m.startTime BETWEEN CURRENT_DATE AND sysdate + 30/(24*60)
        ORDER BY m.startTime DESC
    """)
    fun findAllAcceptMatch(homeClub: Long): MutableList<Match>
}
