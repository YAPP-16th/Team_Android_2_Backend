package com.teamplay.domain.business.user.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.user.repository.UserRepository
import com.teamplay.domain.database.user.entity.User
import java.util.*

class FindByUserEmail(
    private val userRepository: UserRepository
): Function<String, User> {
    override fun apply(email: String): User {

        return userRepository.findByEmail(email).get()
    }

}