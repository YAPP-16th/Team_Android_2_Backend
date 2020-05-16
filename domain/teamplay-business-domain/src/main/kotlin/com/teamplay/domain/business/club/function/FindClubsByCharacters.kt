package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.club.dto.CharactersAndPage
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

class FindClubsByCharacters(
    private val clubRepository: ClubRepository
): Function<CharactersAndPage, Page<Club>> {
    override fun apply(charactersAndPage: CharactersAndPage): Page<Club> {
        return clubRepository.findAllByCharactersIn(charactersAndPage.characters, PageRequest.of(charactersAndPage.page, 10))
    }
}