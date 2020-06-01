package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.core.function.error.InvalidSpecError
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging
import java.time.LocalDateTime
import java.time.format.DateTimeParseException


class CheckValidMatchSpec: ValidatorWithError<MatchSpecs>(InvalidSpecError()) {
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
