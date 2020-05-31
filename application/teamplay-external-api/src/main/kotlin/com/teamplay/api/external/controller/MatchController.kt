package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.request.CreateMatch
import com.teamplay.api.com.teamplay.api.external.request.CreateMatchRequest
import com.teamplay.api.com.teamplay.api.external.request.EnterMatchResultRequest
import com.teamplay.api.com.teamplay.api.external.response.MatchListResponse
import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.api.com.teamplay.api.external.service.MatchService
import com.teamplay.domain.business.match.dto.MatchDetailResultDto
import com.teamplay.domain.business.match.dto.MatchIndividualResultDto
import com.teamplay.domain.business.match.dto.MatchInfo
import com.teamplay.domain.business.match.dto.MatchSummaryResult
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$baseUrl/matches")
class MatchController (
        private val matchService: MatchService
) {
    @ApiOperation(value = "매칭 게시글 보기")
    @GetMapping
    fun findMatches(@ModelAttribute specs: MatchSpecs): MatchListResponse {
        return matchService.getMatchesList(specs)
    }

    @ApiOperation(value = "매칭 상세 글 보기")
    @GetMapping("/{matchId}")
    fun getMatch(@PathVariable matchId: Long): MatchInfo {
        return matchService.getMatchInfo(matchId)
    }

    @ApiOperation(value = "매칭 스케쥴 보기")
    @GetMapping("/schedule/{clubId}")
    fun getSchedule(@PathVariable clubId: Long): MatchScheduleResponse {
        return matchService.getMatchSchedule(clubId)
    }

    @ApiOperation(value = "매치 요약 결과 보기")
    @GetMapping("/{matchId}/result/summary")
    fun getSummaryResult(@PathVariable matchId: Long): MutableList<MatchSummaryResult> {
        return matchService.getMatchSummaryResult(matchId)
    }

    @ApiOperation(value = "매치 상세 결과 보기")
    @GetMapping("/{matchId}/result/detail")
    fun getDetailResult(@PathVariable matchId: Long): MatchDetailResultDto {
        return matchService.getMatchDetailResult(matchId)
    }

    @ApiOperation(value = "매치 개인 기록 보기")
    @GetMapping("/{matchId}/result/individual")
    fun getIndividualResult(@PathVariable matchId: Long): MutableList<MatchIndividualResultDto> {
        return matchService.getMatchIndividualResult(matchId)
    }

    @ApiOperation(value = "매칭 게시글 작성")
    @PostMapping
    fun saveMatch(@RequestBody createMatch: CreateMatch): Match {
        return matchService.createMatch(createMatch)
    }

    @ApiOperation(value = "시합 요청")
    @PostMapping("/{matchId}/matchRequest")
    fun requestMatch(@PathVariable matchId: Long, @RequestBody createMatchRequest: CreateMatchRequest): MatchRequest {
        return matchService.saveMatchRequest(matchId, createMatchRequest)
    }

    @ApiOperation(value = "시합 결과 입력")
    @PostMapping("/{matchId}/matchResult")
    fun enterMatchResult(@PathVariable matchId: Long, @RequestBody enterMatchResultRequest: EnterMatchResultRequest): Match {
        return matchService.enterMatchResult(matchId, enterMatchResultRequest)
    }

    @ApiOperation(value = "시합 요청 응답")
    @PutMapping("/{matchRequestId}/matchResponse")
    fun responseMatch(@PathVariable matchRequestId: Long, @RequestBody matchRequestStatus: MatchRequestStatus): Match {
        return matchService.responseMatchRequest(matchRequestId, matchRequestStatus)
    }


}

