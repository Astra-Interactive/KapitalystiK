package ru.astrainteractive.kapitalystic.exposed.api

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
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
    override fun isUserInvited(userDTO: UserDTO, orgID: Long): Boolean = transaction {
        !InvitationDAO.find(
            InvitationTable.orgID.eq(orgID).and {
                InvitationTable.minecraftUUID.eq(userDTO.minecraftUUID.toString())
            }
        ).empty()
    }

    override fun isMember(userDTO: UserDTO): Boolean = transaction {
        val expression = MemberTable.minecraftUUID.eq(userDTO.minecraftUUID.toString())
        !MemberDAO.find(expression).empty()
    }

    override fun isOwner(userDTO: UserDTO): Boolean = transaction {
        val memberDTO = fetchMember(userDTO)
        val orgDTO = fetchOrg(memberDTO)
        orgDTO.leader.id == memberDTO.id
    }

    override fun fetchMember(userDTO: UserDTO): MemberDTO = transaction {
        val expression = MemberTable.minecraftUUID
            .eq(userDTO.minecraftUUID.toString())
        val memberDAO = MemberDAO.find(expression).firstOrNull() ?: throw DBException.NotOrganizationMember
        memberDAO.let(memberMapper::toDTO)
    }

    override fun fetchOrg(userDTO: UserDTO): OrganizationDTO = transaction {
        val memberDTO = fetchMember(userDTO)
        fetchOrg(memberDTO)
    }

    override fun fetchOrg(memberDTO: MemberDTO): OrganizationDTO = transaction {
        val orgDAO = OrgDAO.findById(memberDTO.orgID) ?: throw DBException.NotOrganizationMember
        orgDAO.let(orgMapper::toDTO)
    }

    override fun fetchOrg(orgTAG: String): OrganizationDTO = transaction {
        val orgDAO = OrgDAO.find(OrgTable.tag.eq(orgTAG)).firstOrNull()
        orgDAO ?: throw DBException.UnexpectedException
        orgDAO.let(orgMapper::toDTO)
    }
}
