package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.match.error.MatchRequestStatusError
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class CheckIsWaitingMatchRequestById(
    private val repository: MatchRequestRepository
): ValidatorWithError<Long>(MatchRequestStatusError()) {
    override fun apply(matchId: Long): Boolean {
        return repository.checkIsWaitingMatchRequestById(matchId)
    }
}
