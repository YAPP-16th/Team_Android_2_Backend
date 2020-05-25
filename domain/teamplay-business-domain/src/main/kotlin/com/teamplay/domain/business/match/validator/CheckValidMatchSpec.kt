package com.teamplay.domain.business.match.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.core.function.error.InvalidSpecError
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import mu.KLogging

class CheckValidMatchSpec: ValidatorWithError<MatchSpecs>(InvalidSpecError()) {
    override fun apply(matchSpecs: MatchSpecs): Boolean {
        val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

        return matchSpecs.let {it.page > 0 && it.rowsPerPage > 0 && Match::class.java.declaredFields.map { field ->
                camelRegex.replace(field.name) { column ->
                    "_${column.value}"
                }.toLowerCase()
            }.contains(it.sortBy)
        }.apply{ logger.error("Match spec is invalid. page : {}, rowsPerPage : {}, sortBy : {}", matchSpecs.page, matchSpecs.rowsPerPage, matchSpecs.sortBy) }
    }

    companion object: KLogging()
}
