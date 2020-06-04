package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.match.dto.MatchDTO.*
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import java.text.SimpleDateFormat

class MatchResponse{
    class MatchListResponse (
            val currentPage: Int,
            val totalPage: Int,
            val filterTitle: String,
            val matchList: MutableList<MatchListInfoDTO>
    )

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
    }

    class MatchScheduleResponse(
            val matchSchedule: MutableList<MatchScheduleListDTO>
    )

    class MatchSummaryResponse(
        val matchSummaryResult: MutableList<MatchSummaryResultDTO>
    )

    class MatchDetailResultResponse(
        val matchDetailResult: MatchDetailResultDTO
    )

    class MatchIndividualResultResponse(
        val matchIndividualResult: MutableList<MatchIndividualResultDTO>
    )

    class MatchCreateResponse(
        val match: Match
    )

    class MatchRequestResponse(
        val matchRequest: MatchRequest
    )

    companion object {
        private const val MMDD_WITH_SLASH = "yyyy년 MM월 dd일"
        private const val HHMM_WITH_SLASH = "hh:mm"
    }
}
