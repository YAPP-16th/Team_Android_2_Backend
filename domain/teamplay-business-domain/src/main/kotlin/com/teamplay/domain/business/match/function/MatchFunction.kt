package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.MatchDTO.*
import com.teamplay.domain.business.match.error.MatchIsNotExistError
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import mu.KLogging
import org.springframework.data.domain.Page
import org.springframework.transaction.annotation.Transactional

class MatchFunction(
        private val matchRepository: MatchRepository,
        private val matchRequestRepository: MatchRequestRepository
) {
    fun getMatchById(matchId: Long): Match = GetMatchById().apply(matchId)
    fun getMatchScheduleByClubId(matchScheduleRequestDTO: MatchScheduleRequestDTO): MutableList<Match> = GetMatchScheduleByClubId().apply(matchScheduleRequestDTO)
    fun getHostMatchByClubId(clubId: Long): MutableList<MatchRequest> = GetHostMatchByClubId().apply(clubId)
    fun getGuestMatchByClubId(clubId: Long): MutableList<MatchRequest> = GetGuestMatchByClubId().apply(clubId)
    fun getMatchIdByMatchRequestId(matchRequestId: Long): Long = GetMatchIdByMatchRequestId().apply(matchRequestId)
    fun getRecentlyRecordByClubId(clubId: Long): MutableList<Boolean> = GetRecentlyRecordByClubId().apply(clubId)
    fun findAllMatchByMatchSpec(specs: MatchSpecs): Page<Match> = FindAllMatchByMatchSpec().apply(specs)
    fun saveMatch(match: Match): Match = SaveMatch().apply(match)
    fun updateMatchRequest(updateMatchRequestDto: UpdateMatchRequestDTO): Match = UpdateMatchRequest().apply(updateMatchRequestDto)

    private inner class GetMatchById: Function<Long, Match> {
        override fun apply(matchId: Long): Match {
            return matchRepository.findById(matchId).orElseThrow {
                logger.warn("Request match id : {} is not exist", matchId)
                throw MatchIsNotExistError()
            }
        }
    }

    private inner class GetMatchScheduleByClubId: Function<MatchScheduleRequestDTO, MutableList<Match>> {
        override fun apply(matchScheduleRequestDTO: MatchScheduleRequestDTO): MutableList<Match> {
            return matchRepository.findAllAcceptMatchByBetweenDate(
                    matchScheduleRequestDTO.id,
                    matchScheduleRequestDTO.startDate,
                    matchScheduleRequestDTO.endDate
            )
        }
    }

    private inner class GetHostMatchByClubId: Function<Long, MutableList<MatchRequest>> {
        override fun apply(clubId: Long): MutableList<MatchRequest> {
            return matchRequestRepository.findAllHostMatch(clubId)
        }
    }

    private inner class GetGuestMatchByClubId: Function<Long, MutableList<MatchRequest>> {
        override fun apply(clubId: Long): MutableList<MatchRequest> {
            return matchRequestRepository.findAllGuestMatch(clubId)
        }
    }

    private inner class GetMatchIdByMatchRequestId: Function<Long, Long> {
        override fun apply(matchRequestId: Long): Long {
            return matchRequestRepository.getMatchIdByMatchRequestId(matchRequestId)
        }
    }

    private inner class GetRecentlyRecordByClubId: Function<Long, MutableList<Boolean>> {
        override fun apply(clubId: Long): MutableList<Boolean> {
            return matchRepository.getRecentlyRecordById(clubId).take(5).map {
                it.winner?.id == clubId
            }.toMutableList()
        }
    }

    private inner class FindAllMatchByMatchSpec: Function<MatchSpecs, Page<Match>> {
        override fun apply(specs: MatchSpecs): Page<Match> {
            return matchRepository.findAll(specs.createSpecs(), specs.createPageRequest())
        }
    }

    private open inner class SaveMatch: Function<Match, Match> {
        @Transactional
        override fun apply(match: Match): Match {
            return matchRepository.save(match.prepareForSave())
        }
    }

    private open inner class UpdateMatchRequest: Function<UpdateMatchRequestDTO, Match> {
        @Transactional
        override fun apply(updateMatchRequestDto: UpdateMatchRequestDTO): Match {
            return matchRepository.getByMatchRequests_Id(updateMatchRequestDto.matchRequestId).let { match ->
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

    companion object: KLogging()
}
