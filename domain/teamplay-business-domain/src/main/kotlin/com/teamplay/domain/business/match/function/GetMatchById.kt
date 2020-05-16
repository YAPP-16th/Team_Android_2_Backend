package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match

class GetMatchById(
    private val matchRepository: MatchRepository
): Function<Long, Match> {
    override fun apply(id: Long): Match {

        return matchRepository.findById(id).get()
    }

}
