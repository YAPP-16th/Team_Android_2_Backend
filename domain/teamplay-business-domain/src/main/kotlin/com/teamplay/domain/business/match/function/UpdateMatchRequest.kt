package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.UpdateMatchRequestDto
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import org.springframework.transaction.annotation.Transactional

open class UpdateMatchRequest(
    private val matchRepository: MatchRepository
): Function<UpdateMatchRequestDto, Match> {
    @Transactional
    override fun apply(updateMatchRequestDto: UpdateMatchRequestDto): Match {
        return matchRepository.getByMatchRequests(updateMatchRequestDto.matchRequestId).let { match ->
            if (updateMatchRequestDto.matchRequestStatus == MatchRequestStatus.ACCEPT) {
                match.matchStatus = MatchStatus.CLOSE

                match.matchRequests?.map{
                    if (it.id != updateMatchRequestDto.matchRequestId) {
                        it.matchRequestStatus = MatchRequestStatus.REJECT
                    }
                }
            }
            match.matchRequests?.map{ it.id }?.indexOf(updateMatchRequestDto.matchRequestId)?.let {
                match.matchRequests?.get(it)?.matchRequestStatus = updateMatchRequestDto.matchRequestStatus
            }

            match
        }
    }

}
