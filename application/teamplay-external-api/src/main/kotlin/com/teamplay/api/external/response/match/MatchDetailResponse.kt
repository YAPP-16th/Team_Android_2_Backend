package com.teamplay.api.com.teamplay.api.external.response.match

import com.teamplay.domain.database.match.entity.Match
import java.text.SimpleDateFormat

class MatchDetailResponse (
    val id: Long,
    val title: String,
    val hostName: String,
    val matchDate: String,
    val matchTime: String,
    val location: String,
    val matchStyle: String,
    val introduce: String
) {
    constructor(match: Match): this(
        id = match.id!!,
        title = match.title,
        hostName = match.host.name,
        matchDate = SimpleDateFormat(MMDD_WITH_SLASH).format(match.startTime),
        matchTime = SimpleDateFormat(HHMM_WITH_SLASH).format(match.startTime) + " - " +
                SimpleDateFormat(HHMM_WITH_SLASH).format(match.endTime),
        location = match.location,
        matchStyle = match.matchStyle.toString(),
        introduce = match.introduce
    )

    companion object {
        private const val MMDD_WITH_SLASH = "yyyy년 MM월 dd일"
        private const val HHMM_WITH_SLASH = "hh:mm"
    }
}
