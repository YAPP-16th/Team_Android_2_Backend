package com.teamplay.domain.business.club.error

import com.teamplay.core.function.error.NotFoundError

class ClubIsNotExistError(message: String = "club is not exist") : NotFoundError(message)