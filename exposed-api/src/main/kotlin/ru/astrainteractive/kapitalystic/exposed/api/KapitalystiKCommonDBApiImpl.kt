package ru.astrainteractive.kapitalystic.exposed.api

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.dto.MemberDTO
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable
import ru.astrainteractive.kapitalystic.exposed.api.map.MemberMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.MemberMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.OrgMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.OrgMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapperImpl

internal class KapitalystiKCommonDBApiImpl(
    private val warpMapper: WarpMapper = WarpMapperImpl(),
    private val memberMapper: MemberMapper = MemberMapperImpl,
    private val orgMapper: OrgMapper = OrgMapperImpl(
        warpMapper = warpMapper,
        memberMapper = memberMapper,
    ),
) : KapitalystiKCommonDBApi {
    override suspend fun isUserInvited(userDTO: UserDTO, orgID: Long): Boolean {
        return !InvitationDAO.find(
            InvitationTable.orgID.eq(orgID).and {
                InvitationTable.minecraftUUID.eq(userDTO.minecraftUUID.toString())
            }
        ).empty()
    }

    override suspend fun isMember(userDTO: UserDTO): Boolean {
        val expression = MemberTable.minecraftUUID.eq(userDTO.minecraftUUID.toString())
        return !MemberDAO.find(expression).empty()
    }

    override suspend fun isOwner(userDTO: UserDTO): Boolean {
        val memberDTO = fetchMember(userDTO)
        val orgDTO = fetchOrg(memberDTO)
        return orgDTO.leader.id == memberDTO.id
    }

    override suspend fun fetchMember(userDTO: UserDTO): MemberDTO {
        val expression = MemberTable.minecraftUUID
            .eq(userDTO.minecraftUUID.toString())
        val memberDAO = MemberDAO.find(expression).firstOrNull() ?: throw DBException.NotOrganizationMember
        return memberDAO.let(memberMapper::toDTO)
    }

    override suspend fun fetchOrg(userDTO: UserDTO): OrganizationDTO {
        val memberDTO = fetchMember(userDTO)
        return fetchOrg(memberDTO)
    }

    override suspend fun fetchOrg(memberDTO: MemberDTO): OrganizationDTO {
        val orgDAO = OrgDAO.findById(memberDTO.orgID) ?: throw DBException.NotOrganizationMember
        return orgDAO.let(orgMapper::toDTO)
    }

    override suspend fun fetchOrg(orgTAG: String): OrganizationDTO {
        val orgDAO = OrgDAO.find(OrgTable.tag.eq(orgTAG)).firstOrNull()
        orgDAO ?: throw DBException.UnexpectedException
        return orgDAO.let(orgMapper::toDTO)
    }
}
