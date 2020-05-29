package com.teamplay.domain.database.club.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "club_members")
data class ClubMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "club_id")
    var club: Club,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    var user: User
): EntityId
