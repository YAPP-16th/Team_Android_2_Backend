package com.teamplay.domain.database.match.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @OneToOne
    val home: Club,

    @OneToOne
    val away: Club? = null,

    @Column(nullable = false)
    val location: String,

    @Column(nullable = false)
    val startTime: Date,

    @Column(nullable = false)
    val endTime: Date,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val matchStyle: MatchStyle,

    val homeScore: Int? = null,

    val awayScore: Int? = null,

    @Enumerated(EnumType.STRING)
    var matchStatus: MatchStatus = MatchStatus.WAITING,

    @OneToMany(mappedBy = "match", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val matchRequests: MutableList<MatchRequest>? = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "winner_id")
    val winner: Club? = null,

    @CreationTimestamp
    val createdDate: Date? = Date(),

    @CreationTimestamp
    val modifiedDate: Date? = Date()
): EntityId {
    fun prepareForSave(): Match {
        this.matchRequests?.forEach { it.match = this }
        return this
    }
}
