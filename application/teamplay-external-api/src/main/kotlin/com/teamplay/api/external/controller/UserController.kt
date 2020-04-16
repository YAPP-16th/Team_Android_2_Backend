package com.teamplay.api.com.teamplay.api.external.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @ApiOperation(value = "유저 정보")
    @GetMapping("/principal")
    fun getUserPrincipal(): String {
        return "유저 정보"
    }

    @ApiOperation(value = "가입 성공 여부")
    @PostMapping("/join")
    fun save(): String {
        return "가입 성공 여부"
    }
}