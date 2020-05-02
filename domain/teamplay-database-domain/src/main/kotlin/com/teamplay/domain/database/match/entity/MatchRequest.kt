package com.teamplay.domain.database.match.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import javax.persistence.*

@Entity
@Table(name = "match_requests")
data class MatchRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @OneToOne
    @JoinColumn(name = "match_id")
    var match: Match,

    @OneToOne
    @JoinColumn(name = "requester_id")
    var requester: Club
    ): EntityId