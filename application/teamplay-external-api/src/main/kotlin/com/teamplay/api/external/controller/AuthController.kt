package com.teamplay.api.com.teamplay.api.external.controller

import com.teamplay.api.com.teamplay.api.external.config.baseUrl
import com.teamplay.api.com.teamplay.api.external.request.SignInByEmailRequest
import com.teamplay.api.com.teamplay.api.external.request.SignUpByEmailRequest
import com.teamplay.api.com.teamplay.api.external.response.SignInResponse
import com.teamplay.api.com.teamplay.api.external.service.AuthService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$baseUrl/auth")
class AuthController {
    @Autowired
    private lateinit var authService: AuthService

    @ApiOperation(value = "로그인")
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@RequestBody signInByEmailRequest: SignInByEmailRequest): SignInResponse {
        return authService.signInByEmail(signInByEmailRequest)
    }

    @ApiOperation(value = "회원가입")
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    fun signUp(@RequestBody signUpByEmailRequest: SignUpByEmailRequest): SignInResponse {
        return authService.signUpByEmail(signUpByEmailRequest)
    }

    @ApiOperation(value = "액세스 토큰 재발급")
    @GetMapping("/access-token")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun refreshAccessToken(@Valid @RequestHeader refreshToken: String): String {
        return "엑세스 토큰 재발급"
    }
}