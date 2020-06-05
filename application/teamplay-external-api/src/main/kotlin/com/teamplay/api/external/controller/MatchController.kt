package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.request.match.CreateMatch
import com.teamplay.api.com.teamplay.api.external.request.match.CreateMatchRequest
import com.teamplay.api.com.teamplay.api.external.request.match.EnterMatchResultRequest
import com.teamplay.api.com.teamplay.api.external.response.match.*
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.api.com.teamplay.api.external.service.MatchService
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$baseUrl/matches")
class MatchController (
    private val matchService: MatchService,
    private val authService: AuthService
) {
    @ApiOperation(value = "매칭 게시글 보기")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findMatches(@ModelAttribute specs: MatchSpecs): MatchListResponse {
        return matchService.getMatchesList(specs)
    }

    @ApiOperation(value = "매칭 상세 글 보기")
    @GetMapping("/{matchId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMatch(@PathVariable matchId: Long): MatchDetailResponse {
        return matchService.getMatchInfo(matchId)
    }

    @ApiOperation(value = "매칭 스케쥴 보기")
    @GetMapping("/schedule/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    fun getSchedule(@PathVariable clubId: Long): MatchScheduleResponse {
        return matchService.getMatchSchedule(clubId)
    }

    @ApiOperation(value = "매치 요약 결과 보기")
    @GetMapping("/{matchId}/result/summary")
    @ResponseStatus(HttpStatus.OK)
    fun getSummaryResult(@PathVariable matchId: Long): MatchSummaryResponse {
        return MatchSummaryResponse(
                matchService.getMatchSummaryResult(matchId)
        )
    }

    @ApiOperation(value = "매치 상세 결과 보기")
    @GetMapping("/{matchId}/result/detail")
    @ResponseStatus(HttpStatus.OK)
    fun getDetailResult(@PathVariable matchId: Long): MatchDetailResultResponse {
        return MatchDetailResultResponse(
                matchService.getMatchDetailResult(matchId)
        )
    }

    @ApiOperation(value = "매치 개인 기록 보기")
    @GetMapping("/{matchId}/result/individual")
    @ResponseStatus(HttpStatus.OK)
    fun getIndividualResult(@PathVariable matchId: Long): MatchIndividualResultResponse {
        return MatchIndividualResultResponse(
                matchService.getMatchIndividualResult(matchId)
        )
    }

    @ApiOperation(value = "매칭 게시글 작성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveMatch(
            @Valid @RequestHeader accessToken: String,
            @RequestBody createMatch: CreateMatch
    ): MatchCreateResponse {
        val user = authService.getUserByAccessToken(accessToken)

        return MatchCreateResponse(
                matchService.createMatch(createMatch, user)
        )
    }

    @ApiOperation(value = "시합 요청")
    @PostMapping("/{matchId}/matchRequest")
    @ResponseStatus(HttpStatus.CREATED)
    fun requestMatch(
            @Valid @RequestHeader accessToken: String,
            @PathVariable matchId: Long,
            @RequestBody createMatchRequest: CreateMatchRequest
    ): MatchRequestResponse {
        val user = authService.getUserByAccessToken(accessToken)

        return MatchRequestResponse(
                matchService.saveMatchRequest(matchId, createMatchRequest, user)
        )
    }

    @ApiOperation(value = "시합 결과 입력")
    @PostMapping("/{matchId}/matchResult")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun enterMatchResult(@Valid @RequestHeader accessToken: String,
                         @PathVariable matchId: Long,
                         @RequestBody enterMatchResultRequest: EnterMatchResultRequest
    ) {
        val user = authService.getUserByAccessToken(accessToken)

        matchService.enterMatchResult(matchId, enterMatchResultRequest, user)
    }

    @ApiOperation(value = "시합 요청 응답")
    @PutMapping("/{matchRequestId}/matchResponse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun responseMatch(@PathVariable matchRequestId: Long, @RequestBody matchRequestStatus: MatchRequestStatus) {
        matchService.responseMatchRequest(matchRequestId, matchRequestStatus)
    }


}

