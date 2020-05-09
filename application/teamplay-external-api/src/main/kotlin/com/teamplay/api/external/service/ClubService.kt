package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.CreateClubRequest
import com.teamplay.api.com.teamplay.api.external.request.GetClubsRequest
import com.teamplay.api.com.teamplay.api.external.response.ClubResponse
import com.teamplay.api.com.teamplay.api.external.response.ClubsResponse
import com.teamplay.api.com.teamplay.api.external.response.CreateClubResponse
import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.club.dto.NameAndPage
import com.teamplay.domain.business.club.function.*
import com.teamplay.domain.business.club.validator.CheckDuplicateClubName
import com.teamplay.domain.business.club.validator.CheckExistClub
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
    private val findClubsByName = FindClubsByName(clubRepository)
    private val findClubMembersByClubId = FindClubMembersByClubId(clubMemberRepository)
    private val findClubAdminsByClubId = FindClubAdminsByClubId(clubAdminRepository)

    private val checkDuplicateClubName = CheckDuplicateClubName(clubRepository)
    private val checkExistClub = CheckExistClub(clubRepository)

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

    fun findClub(clubId: Long): ClubResponse{
        checkExistClub.verify(clubId)
        val members = clubMemberToUserInfo(findClubMembers(clubId))
        val admins = clubAdminToUserInfo(findClubAdmins(clubId))
        val club = findClubById(clubId)

        return ClubResponse(
            clubInfo = ClubInfo(club),
            admins = admins,
            members = members
        )
    }

    fun findClubMembers(clubId: Long): MutableList<ClubMember> {
        return findClubMembersByClubId(clubId)
    }

    fun findClubAdmins(clubId: Long): MutableList<ClubAdmin> {
        return findClubAdminsByClubId(clubId)
    }

    fun findClubInfosByName(getClubsRequest: GetClubsRequest): ClubsResponse{
        val clubsPage = findClubsByName(NameAndPage(getClubsRequest.name, getClubsRequest.currentPage))
        val clubs = clubsPage.content
        return ClubsResponse(
            clubsToClubInfos(clubs),
            clubsPage.totalPages,
            clubsPage.number
        )
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

    private fun clubsToClubInfos(clubs: MutableList<Club>): MutableList<ClubInfo>{
        var clubInfos = mutableListOf<ClubInfo>()
        for(club in clubs){
            clubInfos.add(ClubInfo(club))
        }

        return clubInfos
    }
}