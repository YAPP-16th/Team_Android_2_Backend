package com.teamplay.domain.business.club.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.club.dto.ClubIdAndUserId
import com.teamplay.domain.business.club.error.UserAlreadyRegisteredClubError
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository

class CheckAlreadyRegisteredClub(
    private val clubMemberRepository: ClubMemberRepository
): ValidatorWithError<ClubIdAndUserId>(UserAlreadyRegisteredClubError()) {
    override fun apply(clubIdAndUserId: ClubIdAndUserId): Boolean {
        return !clubMemberRepository.existsByClub_IdAndUser_Id(clubIdAndUserId.clubId, clubIdAndUserId.userId);
    }
}