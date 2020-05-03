package com.teamplay.domain.business.user.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User

class FindUserById(
    private val userRepository: UserRepository
): Function<Long, User> {
    override fun apply(userId: Long): User {

        return userRepository.findById(userId).get()
    }

}