package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.core.function.error.InvalidSpecError
import com.teamplay.domain.business.match.dto.MatchValidatorDTO.*
import com.teamplay.domain.business.match.error.*
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class MatchValidation(
        private val matchRepository: MatchRepository,
        private val matchRequestRepository: MatchRequestRepository
) {
    fun checkAlreadyRequestMatch(checkAlreadyRequestMatchDto: CheckAlreadyRequestMatchDTO) = CheckAlreadyRequestMatch().apply(checkAlreadyRequestMatchDto)
    fun checkExistMatch(matchId: Long) = CheckExistMatch().apply(matchId)
    fun checkExistMatchRequest(matchRequestId: Long) = CheckExistMatchRequest().apply(matchRequestId)
    fun checkIsCloseMatchById(matchId: Long) = CheckIsCloseMatchById().apply(matchId)
    fun checkIsEndMatchById(matchId: Long) = CheckIsEndMatchById().apply(matchId)
    fun checkIsWaitingMatchById(matchId: Long) = CheckIsWaitingMatchById().apply(matchId)
    fun checkIsWaitingMatchRequestById(matchId: Long) = CheckIsWaitingMatchRequestById().apply(matchId)
    fun checkValidMatchSpec(matchSpecs: MatchSpecs) = CheckValidMatchSpec().apply(matchSpecs)

    private inner class CheckAlreadyRequestMatch: ValidatorWithError<CheckAlreadyRequestMatchDTO>(MatchRequestIsAlreadyExistError()) {
        override fun apply(checkAlreadyRequestMatchDto: CheckAlreadyRequestMatchDTO): Boolean {
            return !matchRequestRepository.checkDuplicateRequestMatch(checkAlreadyRequestMatchDto.matchId, checkAlreadyRequestMatchDto.requesterClubId)
        }
    }

    private inner class CheckExistMatch: ValidatorWithError<Long>(MatchIsNotExistError()) {
        override fun apply(matchId: Long): Boolean {
            return matchRepository.existsById(matchId)
        }
    }

    private inner class CheckExistMatchRequest: ValidatorWithError<Long>(MatchRequestIsNotExistError()) {
        override fun apply(matchRequestId: Long): Boolean {
            return matchRequestRepository.existsById(matchRequestId)
        }
    }

    private inner class CheckIsCloseMatchById: ValidatorWithError<Long>(MatchStatusError()) {
        override fun apply(matchId: Long): Boolean {
            return matchRepository.checkIsCloseMatchById(matchId)
        }
    }

    private inner class CheckIsEndMatchById: ValidatorWithError<Long>(MatchStatusError()) {
        override fun apply(matchId: Long): Boolean {
            return matchRepository.checkIsEndMatchById(matchId)
        }
    }

    private inner class CheckIsWaitingMatchById: ValidatorWithError<Long>(MatchStatusError()) {
        override fun apply(matchId: Long): Boolean {
            return matchRepository.checkIsWaitingMatchById(matchId)
        }
    }

    private inner class CheckIsWaitingMatchRequestById: ValidatorWithError<Long>(MatchRequestStatusError()) {
        override fun apply(matchId: Long): Boolean {
            return matchRequestRepository.checkIsWaitingMatchRequestById(matchId)
        }
    }

    private inner class CheckValidMatchSpec: ValidatorWithError<MatchSpecs>(InvalidSpecError()) {
        override fun apply(matchSpecs: MatchSpecs): Boolean {
            val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

            return matchSpecs.let {
                it.page > 0 && it.rowsPerPage > 0
                        && it.startTimeFrom?.let{ date -> isValidStringDate(date) } ?: true
                        && it.startTimeTo?.let{ date -> isValidStringDate(date) } ?: true
                        && Match::class.java.declaredFields.map { field ->
                    camelRegex.replace(field.name) { column ->
                        "_${column.value}"
                    }.toLowerCase()
                }.contains(it.sortBy)
            }.apply{ logger.error("Match spec is invalid. Match spec : {}", matchSpecs) }
        }
    }

    private fun isValidStringDate(date: String): Boolean {
        try {
            LocalDateTime.parse(date)
        } catch (e: DateTimeParseException) {
            return false
        }
        return true
    }

    companion object: KLogging()
}
