package com.teamplay.domain.business.match.dto

class MatchValidatorDTO {
    class CheckAlreadyRequestMatchDTO(
        val matchId: Long,
        val requesterClubId: Long
    )
}
