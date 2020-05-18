package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.match.error.MatchRequestIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class CheckExistMatchRequest(
    private val repository: MatchRequestRepository
): ValidatorWithError<Long>(MatchRequestIsNotExistError()) {

    override fun apply(matchRequestId: Long): Boolean {
        return repository.existsById(matchRequestId)
    }
}
