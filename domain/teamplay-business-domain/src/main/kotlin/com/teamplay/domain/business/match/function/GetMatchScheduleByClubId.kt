package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.MatchScheduleRequest
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match

class GetMatchScheduleByClubId(
    private val matchRepository: MatchRepository
): Function<MatchScheduleRequest, MutableList<Match>> {
    override fun apply(matchScheduleRequest: MatchScheduleRequest): MutableList<Match> {
        return matchRepository.findAllAcceptMatchByBetweenDate(
                matchScheduleRequest.id,
                matchScheduleRequest.startDate,
                matchScheduleRequest.endDate
        )
    }
}
