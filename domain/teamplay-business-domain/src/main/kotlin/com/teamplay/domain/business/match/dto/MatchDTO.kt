package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.business.match.dto.type.MatchEnum.*
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchResultType
import com.teamplay.domain.database.match.entity.MatchStyle
import java.text.SimpleDateFormat
import java.util.*

class MatchDTO {
    class CreateMatchDTO (
            val title: String,
            val startDate: Date,
            val endDate: Date,
            val location: String,
            val matchStyle: MatchStyle,
            val introduce: String
    )

    class DetailResultDTO (
            val hostScore: Int,
            val guestScore: Int,
            val matchResultType: MatchResultType
    )

    class IndividualResultDTO (
            val score: Int,
            val receiver: String,
            val matchResultType: MatchResultType
    )

    class MatchInfoDTO (
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
    }

    class MatchScheduleListDTO (
            val scheduleTitle: String,
            val matchScheduleInfo: MutableList<MatchScheduleInfoDTO>,
            val matchScheduleType: MatchScheduleType
    )

    class MatchScheduleInfoDTO (
            val title: String,
            val description: String,
            val matchDate: String? = null,
            val matchTime: String? = null,
            val requestStatus: MatchRequestStatus? = null,
            val matchRequestId: Long? = null
    )

    class MatchSummaryResultDTO (
            val matchClubType: MatchClubType,
            val totalScore: Int,
            val recentlyRecord: MutableList<Boolean>,
            val isVictory: Boolean
    )

    class MatchDetailResultDTO (
            val hostName: String,
            val guestName: String,
            val matchDetailResultScore: MutableList<MatchDetailResultScoreDTO>
    )

    class MatchIndividualResultDTO (
            val matchResultType: MatchResultType,
            val score: Int,
            val recevier: String
    )

    class MatchDetailResultScoreDTO (
            val matchResultType: MatchResultType,
            val hostScore: Int,
            val guestScore: Int
    )

    class MatchScheduleRequestDTO (
            val id: Long,
            val startDate: Date,
            val endDate: Date
    )

    class UpdateMatchRequestDTO (
            val matchRequestId: Long,
            val matchRequestStatus: MatchRequestStatus
    )

    companion object {
        private const val MMDD_WITH_SLASH = "yyyy년 MM월 dd일"
        private const val HHMM_WITH_SLASH = "hh:mm"
    }
}
