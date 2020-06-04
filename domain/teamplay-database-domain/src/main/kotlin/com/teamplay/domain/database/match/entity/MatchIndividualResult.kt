package com.teamplay.domain.database.match.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.teamplay.core.database.EntityId
import javax.persistence.*

@Entity
@Table(name = "match_individual_result")
data class MatchIndividualResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val resultType: MatchResultType,

    @Column(nullable = false)
    val score: Int,

    @Column(nullable = false)
    val receiver: String
): EntityId {
    @JsonIgnore
    @ManyToOne
    lateinit var match: Match
}
