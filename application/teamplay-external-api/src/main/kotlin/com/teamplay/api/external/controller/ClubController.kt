package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.request.GetClubsRequest
import com.teamplay.api.com.teamplay.api.external.response.ClubResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubsResponse
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import com.teamplay.api.com.teamplay.api.external.service.ClubService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/clubs")
class ClubController {
    @Autowired
    private lateinit var authService: AuthService

    @Autowired
    private lateinit var clubService: ClubService

    @ApiOperation(value = "동호회 생성")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerClub(
        @Valid @RequestHeader(required = false) accessToken: String,
        @RequestBody createClubRequest: CreateClubRequest
    ): CreateClubResponse {
        val user = authService.getUserByAccessToken(accessToken)
        return clubService.registerClub(createClubRequest, user)
    }

    @ApiOperation(value = "동호회 목록 정보")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getClubs(getClubsRequest: GetClubsRequest): ClubsResponse {

        return clubService.findClubInfosByName(getClubsRequest)
    }

    @ApiOperation(value = "동호회 정보")
    @GetMapping("/{clubId}")
    @ResponseStatus(HttpStatus.OK)
    fun getClub(@PathVariable clubId: Long): ClubResponse {
        // 동호회 목록 페이징 처리하여 리턴할지 고민.
        return clubService.findClubAndFeed(clubId)
    }

//    @ApiOperation(value = "가입한 동호회 리스트")
//    @GetMapping("/{userId}")
//    fun getJoinedClub(@PathVariable userId: Long): String {
//        return "가입한 동호회 리스트"
//    }

    @ApiOperation(value = "추천 동호회 리스트")
    @GetMapping("/recommendation")
    fun recommendationClub(): String {
        // 동호회 추천 알고리즘 고민 필요
        return "추천 동호회 리스트"
    }

    @ApiOperation(value = "동호회 뉴스피드")
    @GetMapping("/feed")
    fun getNewsFeed(): String {
        return "동호회 뉴스피드"
    }

    @ApiOperation(value = "피드 생성")
    @PostMapping("/feed")
    fun createFeed(): String {
        return "피드 생성"
    }

    @ApiOperation(value = "동호회 가입 요청")
    @PostMapping("/join")
    fun joinClubRequest(): String {
        // 가입 요청했을 경우, 우선 동호회 테이블에 유저 추가하고 type 으로 가입 대기|멤버|탈퇴 enum 으로 만들고 save 하나만 뚫어두면 좋을거같습니다.
        return "동호회 가입 요청"
    }

    @ApiOperation(value = "동호회에 속한 멤버 상태 변경")
    @PutMapping("/{id}")
    fun updateClubMember(@PathVariable id: Long): String {
        return "동호회에 속한 멤버 상태 변경"
    }
}