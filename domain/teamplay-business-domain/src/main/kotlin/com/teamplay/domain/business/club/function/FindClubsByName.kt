package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.club.dto.NameAndPage
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class FindClubsByName(
    private val clubRepository: ClubRepository
): Function<NameAndPage, Page<Club>> {
    override fun apply(nameAndPageable: NameAndPage): Page<Club> {
        return clubRepository.findAllByNameContaining(nameAndPageable.name, PageRequest.of(nameAndPageable.page, 10))
    }
}