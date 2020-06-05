package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository

class CheckExistMatch(
    private val repository: MatchRepository
): ValidatorWithError<Long>(MatchIsNotExistError()) {
    override fun apply(matchId: Long): Boolean {
        return repository.existsById(matchId)
    }
}
