package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.match.error.MatchStatusError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository

class CheckIsWaitingMatchById(
    private val repository: MatchRepository
): ValidatorWithError<Long>(MatchStatusError()) {
    override fun apply(matchId: Long): Boolean {
        return repository.checkIsWaitingMatchById(matchId)
    }
}
