package com.teamplay.domain.database.match.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "matches")
data class Match(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null,

    @OneToOne
    val home: Club,

    @OneToOne
    var away: Club? = null,

    @OneToOne
    var winner: Club? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val location: String,

    @Column(nullable = false)
    val startTime: Date,

    @Column(nullable = false)
    val endTime: Date,

    @Column(nullable = false)
    val introduce: String,

    var matchResultReview: String? = null,

    var homeScore: Int? = null,

    var awayScore: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val matchStyle: MatchStyle,

    @Enumerated(EnumType.STRING)
    var matchStatus: MatchStatus = MatchStatus.WAITING,

    @OneToMany(mappedBy = "match", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val matchRequests: MutableList<MatchRequest>? = mutableListOf(),

    @OneToMany(mappedBy = "match", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val matchDetailResult: MutableList<MatchDetailResult>? = mutableListOf(),

    @OneToMany(mappedBy = "match", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val matchIndividualResult: MutableList<MatchIndividualResult>? = mutableListOf(),

    @CreationTimestamp
    val createdDate: Date? = Date(),

    @UpdateTimestamp
    val modifiedDate: Date? = Date()
): EntityId {
    fun prepareForSave(): Match {
        this.matchRequests?.forEach { it.match = this }
        this.matchDetailResult?.forEach { it.match = this }
        this.matchIndividualResult?.forEach { it.match = this }
        return this
    }
}
