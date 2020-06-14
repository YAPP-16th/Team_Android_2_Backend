package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match

class FindAllMatchByHostIdFunction(
    private val repository: MatchRepository
): Function<Long, List<Match>> {
    override fun apply(hostId: Long): List<Match> {
        return repository.findAllByHostId(hostId)
    }
}