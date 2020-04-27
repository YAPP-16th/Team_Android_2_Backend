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
    override val id: Int,

    @OneToOne
    @JoinColumn(name = "club_id",insertable=false, updatable=false)
    var home: Club? = null,

    @OneToOne
    @JoinColumn(name = "clubs_id",insertable=false, updatable=false)
    var away: Club?,

    var location: String,

    var startTime: Date,

    var endTime: Date,

    var homeScore: Int,

    var awayScore: Int,

    @OneToOne
    @JoinColumn(name = "clubs_id",insertable=false, updatable=false)
    var winner: Club? = null,

    @CreationTimestamp
    @Column(nullable = false, insertable=false, updatable=false)
    var createDate: Date
): EntityId