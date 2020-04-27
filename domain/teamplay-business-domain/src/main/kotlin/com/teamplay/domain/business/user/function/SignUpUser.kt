package com.teamplay.domain.business.user.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User

class SignUpUser(
    private val userRepository: UserRepository
): Function<User, User> {
    override fun apply(user: User): User {
        return userRepository.save(user)
    }
}