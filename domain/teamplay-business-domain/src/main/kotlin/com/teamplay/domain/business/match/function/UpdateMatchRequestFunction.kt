package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.UpdateMatchRequestDTO
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import org.springframework.transaction.annotation.Transactional

open class UpdateMatchRequestFunction(
    private val repository: MatchRepository
): Function<UpdateMatchRequestDTO, Match> {
    @Transactional
    override fun apply(updateMatchRequestDto: UpdateMatchRequestDTO): Match {
        return repository.getByMatchRequests_Id(updateMatchRequestDto.matchRequestId).let { match ->
            if (updateMatchRequestDto.matchRequestStatus == MatchRequestStatus.ACCEPT) {
                match.matchStatus = MatchStatus.CLOSE

                match.matchRequests?.map{
                    when (it.id == updateMatchRequestDto.matchRequestId) {
                        true -> match.guest = it.requester
                        else -> it.matchRequestStatus = MatchRequestStatus.REJECT
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
