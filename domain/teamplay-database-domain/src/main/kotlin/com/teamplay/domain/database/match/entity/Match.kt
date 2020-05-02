package com.teamplay.domain.database.match.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @OneToOne
    @JoinColumn(name = "home_id")
    var home: Club? = null,

    @OneToOne
    @JoinColumn(name = "away_id")
    var away: Club? = null,

    var location: String,

    var startTime: Date,

    var endTime: Date,

    var homeScore: Int,

    var awayScore: Int,

    @OneToOne
    @JoinColumn(name = "winner_id")
    var winner: Club? = null,

    @CreationTimestamp
    @Column(nullable = false)
    val createdDate: Date
): EntityId