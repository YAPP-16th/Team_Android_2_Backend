package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import org.springframework.data.domain.Page

class FindAllMatchByMatchSpecFunction(
    private val repository: MatchRepository
): Function<MatchSpecs, Page<Match>> {
    override fun apply(specs: MatchSpecs): Page<Match> {
        return repository.findAll(specs.createSpecs(), specs.createPageRequest())
    }
}
