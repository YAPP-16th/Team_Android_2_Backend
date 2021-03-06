package com.teamplay.domain.database.user.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.club.entity.ClubMember
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long?,

    @Email
    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var hashedPassword: String,

    @Column(nullable = false)
    var nickname : String,

    var name: String? = null,
    var age: Int? = null,

    @Enumerated
    var position: Position? = null,
    var height: Int? = null,

    @Enumerated
    var sex: Sex? = null,
    var introduce: String? = null,

    @Enumerated
    val ability: Ability? = null,
    val residence: String? = null,

    @CreationTimestamp
    @Column(nullable = false)
    val signUpDate: Date,

    @UpdateTimestamp
    var updatedDate: Date? = null,

    @OneToMany(mappedBy = "user")
    var memberClubs: MutableList<ClubMember> = mutableListOf<ClubMember>(),

    @OneToMany(mappedBy = "user")
    var adminClubs: MutableList<ClubAdmin> = mutableListOf<ClubAdmin>()

): EntityId
