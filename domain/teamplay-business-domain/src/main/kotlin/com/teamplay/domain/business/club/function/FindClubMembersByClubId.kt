package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.user.entity.User

class FindClubMembersByClubId(
    private val clubMemberRepository: ClubMemberRepository
): Function<Long, MutableList<ClubMember>> {
    override fun apply(clubId: Long): MutableList<ClubMember> {
        return clubMemberRepository.findAllByClubId(clubId)
    }
}