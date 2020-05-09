package com.teamplay.domain.business.user.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class CheckPossibleNickname(
    private val userRepository: UserRepository
): Function<String, Boolean> {
    override fun apply(nicknmae: String): Boolean {
        return userRepository.existsByNickname(nicknmae)
    }
}