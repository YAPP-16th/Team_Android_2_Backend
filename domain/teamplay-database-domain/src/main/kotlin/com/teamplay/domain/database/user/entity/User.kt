package com.teamplay.domain.database.user.entity

import com.teamplay.core.database.EntityId
import com.teamplay.domain.database.club.entity.ClubMember
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
    var email:String,

    @Column(nullable = false)
    var hashedPassword: String,

    @Column(nullable = false)
    var nickName : String,

    var name: String? = null,
    var age: Int? = null,
    var position: Position? = null,
    var height: Int? = null,
    var sex: Sex? = null,
    var introduce: String? = null,
    val ability: Ability? = null,
    val residence: String? = null,

    @Column(nullable = false)
    val signUpDate: Date,
    var updateDate: Date? = null,

    @OneToMany(mappedBy = "users")
    var clubs: MutableList<ClubMember> = mutableListOf<ClubMember>()

): EntityId