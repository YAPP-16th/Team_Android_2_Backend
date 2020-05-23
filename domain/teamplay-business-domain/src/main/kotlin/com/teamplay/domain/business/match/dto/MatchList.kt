package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.Match
import java.text.SimpleDateFormat

class MatchList (
    val title: String,
    val description1: String,
    val description2: String,
    val matchInfo: MatchInfo
) {
    constructor(match: Match): this(
        title = match.title,
        matchInfo = MatchInfo(match),
        description1 = "${match.location} | ${match.matchStyle}",
        description2 = SimpleDateFormat(MMDD_WITH_SLASH).format(match.startTime) + " " +
                SimpleDateFormat(HHMM_WITH_SLASH).format(match.endTime) + " - " +
                SimpleDateFormat(HHMM_WITH_SLASH).format(match.endTime)
    )

    companion object {
        private const val MMDD_WITH_SLASH = "MM월 dd일"
        private const val HHMM_WITH_SLASH = "hh:mm"
    }
}
