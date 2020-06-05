package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging

class GetMatchByIdFunction(
        private val repository: MatchRepository
): Function<Long, Match> {
    override fun apply(matchId: Long): Match {
        return repository.findById(matchId).orElseThrow {
            logger.warn("Request match id : {} is not exist", matchId)
            throw MatchIsNotExistError()
        }
    }

    companion object: KLogging()
}
