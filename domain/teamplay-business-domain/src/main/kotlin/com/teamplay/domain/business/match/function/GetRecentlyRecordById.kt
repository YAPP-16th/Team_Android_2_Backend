package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging

class GetRecentlyRecordById(
    private val matchRepository: MatchRepository
): Function<Long, MutableList<Boolean>> {
    override fun apply(id: Long): MutableList<Boolean> {
        return matchRepository.getRecentlyRecordById(id).take(5).map {
            it.winner?.id == id
        }.toMutableList()
    }

    companion object: KLogging()
}
