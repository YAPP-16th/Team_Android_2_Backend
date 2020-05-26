package com.teamplay.domain.business.club.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.club.dto.CheckIsClubAdminDto
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository

class CheckIsClubAdmin(
        private val repository: ClubAdminRepository
): ValidatorWithError<CheckIsClubAdminDto>(MatchIsNotExistError()) {
    override fun apply(checkIsClubAdminDto: CheckIsClubAdminDto): Boolean {
        return repository.checkIsClubAdmin(checkIsClubAdminDto.userId, checkIsClubAdminDto.clubId)
    }
}
