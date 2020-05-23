package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging

class GetMatchById(
    private val matchRepository: MatchRepository
): Function<Long, Match> {
    override fun apply(id: Long): Match {
        return matchRepository.findById(id).orElseThrow {
            logger.warn("Request match id : {} is not exist", id)
            throw MatchIsNotExistError()
        }
    }

    companion object: KLogging()
}
