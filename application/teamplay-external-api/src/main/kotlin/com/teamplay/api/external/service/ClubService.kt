package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.club.function.CreateClub
import com.teamplay.domain.business.club.function.FindClubById
import com.teamplay.domain.business.club.function.RegisterAdmin
import com.teamplay.domain.business.club.function.RegisterMember
import com.teamplay.domain.business.club.validator.CheckDuplicateClubName
import com.teamplay.domain.business.user.dto.UserInfo
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.club.entity.ClubMember
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository
import com.teamplay.domain.database.jpa.club.repository.ClubMemberRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.user.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClubService @Autowired constructor(
    clubRepository: ClubRepository,
    clubAdminRepository: ClubAdminRepository,
    clubMemberRepository: ClubMemberRepository
){
    private val findClubById = FindClubById(clubRepository)
    private val createClub = CreateClub(clubRepository)
    private val registerAdmin = RegisterAdmin(clubAdminRepository)
    private val registerMember = RegisterMember(clubMemberRepository)

    private val checkDuplicateClubName = CheckDuplicateClubName(clubRepository)

    fun registerClub(createClubRequest: CreateClubRequest, user: User): CreateClubResponse{
        checkDuplicateClubName.verify(createClubRequest.name)
        var club = createClub(requestToClub(createClubRequest))
        club.admin.add(registerAdminInClub(user, club))
        club.members.add(registerMemberClub(user,club))
        return CreateClubResponse(
            ClubInfo(club),
            clubAdminToUserInfo(club.admin),
            clubMemberToUserInfo(club.members)
        )
    }

    fun registerAdminInClub(user:User, club: Club): ClubAdmin{
        return registerAdmin(ClubAdmin(null, club, user))
    }

    fun registerMemberClub(user: User, club: Club): ClubMember {
        return registerMember(ClubMember(null, club, user))
    }

    fun findClub(clubId: Long): Club{
        return findClubById(clubId)
    }

    private fun clubAdminToUserInfo(admins: MutableList<ClubAdmin>): MutableList<UserInfo>{
        var userInfos = mutableListOf<UserInfo>()
        for (admin in admins){
            userInfos.add(UserInfo(admin.user))
        }

        return userInfos
    }

    private fun clubMemberToUserInfo(members: MutableList<ClubMember>): MutableList<UserInfo>{
        var userInfos = mutableListOf<UserInfo>()
        for (member in members){
            userInfos.add(UserInfo(member.user))
        }

        return userInfos
    }

    private fun requestToClub(createClubRequest: CreateClubRequest): Club{
        return Club(
            id = null,
            name = createClubRequest.name,
            category =  createClubRequest.category,
            emblem =  createClubRequest.emblem,
            ability = createClubRequest.ability,
            thumbnail = createClubRequest.thumbnail,
            introduce = createClubRequest.introduce,
            contact = createClubRequest.contact,
            createdDate = Date(),
            createTeamDate = createClubRequest.createTeamDate,
            tags = createClubRequest.tags,
            questions = createClubRequest.questions
        )
    }
}