package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.match.dto.CheckAlreadyRequestMatchDto
import com.teamplay.domain.business.match.error.MatchRequestIsAlreadyExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class CheckAlreadyRequestMatch(
    private val repository: MatchRequestRepository
): ValidatorWithError<CheckAlreadyRequestMatchDto>(MatchRequestIsAlreadyExistError()) {
    override fun apply(checkAlreadyRequestMatchDto: CheckAlreadyRequestMatchDto): Boolean {
        return !repository.checkDuplicateRequestMatch(checkAlreadyRequestMatchDto.matchId, checkAlreadyRequestMatchDto.requesterClubId)
    }
}
