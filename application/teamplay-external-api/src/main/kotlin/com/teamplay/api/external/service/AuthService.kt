package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.SignUpByEmailRequest
import com.teamplay.api.com.teamplay.api.external.response.SignInResponse
import com.teamplay.domain.business.token.function.EncodeToken
import com.teamplay.domain.business.token.function.GenerateAccessToken
import com.teamplay.domain.business.token.function.GenerateAccessTokenByUser
import com.teamplay.domain.business.token.function.GenerateRefreshTokenByUser
import com.teamplay.domain.business.user.dto.UserInfo
import com.teamplay.domain.business.user.function.SignUpUser
import com.teamplay.domain.business.user.validator.CheckDuplicateUserEmail
import com.teamplay.domain.business.user.validator.CheckDuplicateUserNickname
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService @Autowired constructor(
    @Value("\${JWT_SECRET}")
    private val jwtSecretKey : String,
    @Value("\${JWT_EXPIRATION}")
    private val jwtExpirationInMs: Int,
    userRepository: UserRepository
) {


    private val signUpUser = SignUpUser(userRepository)
    private val generateAccessTokenByUser = GenerateAccessTokenByUser(jwtExpirationInMs)
    private val generateRefreshTokenByUser = GenerateRefreshTokenByUser()
    private val encodeToken = EncodeToken(jwtSecretKey)


    private val checkDuplicateUserEmail = CheckDuplicateUserEmail(userRepository)
    private val checkDuplicateUserNickname = CheckDuplicateUserNickname(userRepository)


    fun signUpByEmail(signUpByEmailRequest: SignUpByEmailRequest): SignInResponse{
        checkDuplicateUserEmail.verify(signUpByEmailRequest.email)
        checkDuplicateUserNickname.verify(signUpByEmailRequest.nickname)

        val user = signUpUser(
            User(
            id = null,
            email = signUpByEmailRequest.email,
            nickname = signUpByEmailRequest.nickname,
            hashedPassword = signUpByEmailRequest.hashedPassword,
            signUpDate = Date()
            )
        )

       return SignInResponse(
           generateAccessToken(user),
           generateRefreshToken(user),
           UserInfo(user.id, user.nickname, user.email)
       )
    }

    private fun generateAccessToken(user: User): String{
        return encodeToken(generateAccessTokenByUser(user))
    }

    private fun generateRefreshToken(user: User): String{
        return encodeToken(generateRefreshTokenByUser(user))
    }
}