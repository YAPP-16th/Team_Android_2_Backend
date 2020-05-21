package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match

class GetMatchScheduleByClubId(
    private val matchRepository: MatchRepository
): Function<Long, MutableList<Match>> {
    override fun apply(id: Long): MutableList<Match> {
        return matchRepository.findAllAcceptMatch(id)
    }

}
