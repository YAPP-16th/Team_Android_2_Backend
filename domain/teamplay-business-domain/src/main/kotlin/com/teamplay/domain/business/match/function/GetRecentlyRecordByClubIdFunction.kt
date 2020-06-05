package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository

class GetRecentlyRecordByClubIdFunction(
    private val repository: MatchRepository
): Function<Long, MutableList<Boolean>> {
    override fun apply(clubId: Long): MutableList<Boolean> {
        return repository.getRecentlyRecordById(clubId).take(5).map {
            it.winner?.id == clubId
        }.toMutableList()
    }
}
