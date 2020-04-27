package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "club_members")
data class ClubMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Int,

    @ManyToOne
    @JoinColumn(name = "club_id")
    var club: Club,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User
): EntityId