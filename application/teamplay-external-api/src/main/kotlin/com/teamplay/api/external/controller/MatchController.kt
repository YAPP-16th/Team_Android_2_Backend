package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.api.com.teamplay.api.external.service.MatchService
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import io.swagger.annotations.ApiOperation
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$baseUrl/matches")
class MatchController (
        private val matchService: MatchService
) {

    @ApiOperation(value = "매칭 게시글 보기")
    @GetMapping
    fun find(@ModelAttribute specs: MatchSpecs): Page<Match> {
        return matchService.find(specs)
    }

    @ApiOperation(value = "매칭 상세 글 보기")
    @GetMapping("/{matchId}")
    fun get(@PathVariable matchId: Long): Match {
        return matchService.get(matchId)
    }

    @ApiOperation(value = "매칭 스케쥴 보기")
    @GetMapping("/schedule/{clubId}")
    fun getSchedule(@PathVariable clubId: Long): MatchScheduleResponse {
        return matchService.getMatchSchedule(clubId)
    }

    @ApiOperation(value = "매칭 게시글 작성")
    @PostMapping
    fun save(@RequestBody match: Match): Match {
        return matchService.save(match)
    }

    @ApiOperation(value = "시합 요청")
    @PostMapping("/matchRequest/{matchId}")
    fun matchRequest(@PathVariable matchId: Long, @RequestBody matchRequest: MatchRequest): MatchRequest {
        return matchService.saveMatchRequest(matchId, matchRequest)
    }

    @ApiOperation(value = "시합 요청 응답")
    @PutMapping("/matchResponse")
    fun matchResponse(@RequestBody matchRequest: MatchRequest): Match {
        return matchService.responseMatchRequest(matchRequest)
    }
}
