package com.teamplay.domain.database.user.entity

import com.teamplay.core.database.EntityId
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
    override var id: Int?,

    @Email
    @Column(nullable = false)
    var email:String,

    @Column(nullable = false)
    var hashedPassword: String,

    @Column(nullable = false)
    var nickname : String,

    var name: String? = null,
    var age: Int? = null,
    var position: Position? = null,
    var height: Int? = null,
    var sex: Sex? = null,
    var introduce: String? = null,
    val ability: Ability? = null,
    val residence: String? = null,

    @CreationTimestamp
    @Column(nullable = false)
    val signUpDate: Date,

    @UpdateTimestamp
    var updateDate: Date? = null,

    @OneToMany(mappedBy = "user")
    var clubs: MutableList<ClubMember> = mutableListOf<ClubMember>()

): EntityId