package com.teamplay.domain.database.match.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "match_detail_result")
data class MatchDetailResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val resultType: MatchResultType,

    @Column(nullable = false)
    val hostScore: Int,

    @Column(nullable = false)
    val guestScore: Int
): EntityId {
    @JsonIgnore
    @ManyToOne
    lateinit var match: Match
}
