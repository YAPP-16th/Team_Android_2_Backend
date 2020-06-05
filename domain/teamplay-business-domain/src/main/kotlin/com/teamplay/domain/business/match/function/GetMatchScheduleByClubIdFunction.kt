package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.MatchScheduleRequestDTO
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match

class GetMatchScheduleByClubIdFunction(
        private val repository: MatchRepository
): Function<MatchScheduleRequestDTO, MutableList<Match>> {
    override fun apply(matchScheduleRequestDTO: MatchScheduleRequestDTO): MutableList<Match> {
        return repository.findAllAcceptMatchByBetweenDate(
                matchScheduleRequestDTO.id,
                matchScheduleRequestDTO.startDate,
                matchScheduleRequestDTO.endDate
        )
    }
}
