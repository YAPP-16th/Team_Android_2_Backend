package com.teamplay.domain.database.club.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.user.entity.Ability
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clubs")
data class Club(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Int,

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

    @CreationTimestamp
    @Column(nullable = false)
    val createDate: Date,

    @UpdateTimestamp
    var updateDate: Date? = null,

    @OneToMany(mappedBy = "club")
    var members: MutableList<ClubMember> = mutableListOf<ClubMember>()
): EntityId