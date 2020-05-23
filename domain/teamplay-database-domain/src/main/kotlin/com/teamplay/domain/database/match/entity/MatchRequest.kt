package com.teamplay.domain.database.match.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.Club
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "match_requests")
data class MatchRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @OneToOne
    val requester: Club,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var matchRequestStatus: MatchRequestStatus = MatchRequestStatus.WAITING,

    @CreationTimestamp
    @Column(nullable = false)
    val createdDate: Date? = Date(),

    @UpdateTimestamp
    @Column(nullable = false)
    val modifiedDate: Date? = Date()
): EntityId {
    @JsonIgnore
    @ManyToOne
    lateinit var match: Match
}
