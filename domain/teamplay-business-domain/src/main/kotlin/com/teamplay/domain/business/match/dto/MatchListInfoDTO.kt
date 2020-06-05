package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.Match
import java.text.SimpleDateFormat

class MatchListInfoDTO (
    val title: String,
    val id: Long,
    val description1: String,
    val description2: String,
    val matchInfo: MatchInfoDTO
) {
    constructor(match: Match): this(
        title = match.title,
        id = match.id!!,
        matchInfo = MatchInfoDTO(match),
        description1 = "${match.host.name} | ${match.matchStyle}",
        description2 = SimpleDateFormat(MMDD_WITH_SLASH).format(match.startTime) + " " +
                SimpleDateFormat(HHMM_WITH_SLASH).format(match.startTime) + " - " +
                SimpleDateFormat(HHMM_WITH_SLASH).format(match.endTime) + " | " + match.location
    )

    companion object {
        private const val MMDD_WITH_SLASH = "yyyy년 MM월 dd일"
        private const val HHMM_WITH_SLASH = "hh:mm"
    }
}
