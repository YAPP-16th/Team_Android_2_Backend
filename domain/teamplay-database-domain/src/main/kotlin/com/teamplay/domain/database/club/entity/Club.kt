package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.Ability
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clubs")
data class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var category: Category,

    var location: String? = null,

    var emblem: String? = null,

    @Column(nullable = false)
    var ability: Ability,

    var thumbnail: String? = null,

    var introduce: String? = null,

    var contact: String? = null,

    @Column(nullable = false)
    val createDate: Date,
    var updateDate: Date? = null,

    @OneToMany(mappedBy = "clubs")
    var members: MutableList<ClubMember> = mutableListOf<ClubMember>()
): EntityId