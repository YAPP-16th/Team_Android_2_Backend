package com.teamplay.domain.database.match.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,

    @OneToOne
    @JoinColumn(name = "clubs_id")
    var home: Club? = null,

    @OneToOne
    @JoinColumn(name = "clubs_id")
    var away: Club,

    var location: String,

    var startTime: Date,

    var endTime: Date,

    var homeScore: Int,

    var awayScore: Int,

    var winner: Club? = null,

    @Column(nullable = false)
    var createDate: Date
): EntityId