package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository

class RegisterMember(
    private val clubMemberRepository: ClubMemberRepository
): Function<ClubMember, ClubMember> {
    override fun apply(clubMember: ClubMember): ClubMember {
        return clubMemberRepository.save(clubMember)
    }
}