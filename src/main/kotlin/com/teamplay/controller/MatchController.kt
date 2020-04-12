package com.teamplay.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/matches")
class MatchController {

    @ApiOperation(value = "매칭 게시글 전체 보기")
    @GetMapping
    fun find(): String {
        //페이징 처리하여 보여줄지 논의 필요.
        //찾기도 여기로 검색
        //spec 정의해서
        return "매칭 게시글 전체 보기"
    }

    @ApiOperation(value = "매칭 상세 글 보기")
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): String {
        return "매칭 상세 글 보기"
    }

    @ApiOperation(value = "시합 요청")
    @PostMapping("/{id}/request")
    fun requestMatch(@PathVariable id: Long): String {
        // 시합 요청 테이블도 만들면 좋을듯? 거기에서 신청 받을지 말지 타입으로 지정하는것도 좋을듯하네요.
        return "시합 요청"
    }

    @ApiOperation(value = "시합 요청 응답")
    @PutMapping("/{id}/response")
    fun responseMatch(@PathVariable id: Long): String {
        // 시합 요청 테이블도 만들면 좋을듯? 거기에서 신청 받을지 말지 타입으로 지정하는것도 좋을듯하네요.
        return "시합 요청 응답"
    }

    @ApiOperation(value = "매칭 게시글 작성")
    @PostMapping
    fun save(): String {
        return "매칭 게시글 작성"
    }
}
