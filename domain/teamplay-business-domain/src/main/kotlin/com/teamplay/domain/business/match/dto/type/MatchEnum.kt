package com.teamplay.domain.business.match.dto.type

class MatchEnum {
    enum class MatchClubType {
        HOST, GUEST
    }

    enum class MatchScheduleType {
        THIS_SCHEDULE, NEXT_SCHEDULE, HOST, GUEST
    }
}
