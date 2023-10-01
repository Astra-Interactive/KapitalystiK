package ru.astrainteractive.kapitalystik.api.impl

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.exception.DBApiException
import ru.astrainteractive.kapitalystik.api.mapping.MemberMapper
import ru.astrainteractive.kapitalystik.api.mapping.MemberMapperImpl
import ru.astrainteractive.kapitalystik.api.mapping.OrgMapper
import ru.astrainteractive.kapitalystik.api.mapping.OrgMapperImpl
import ru.astrainteractive.kapitalystik.api.mapping.WarpMapper
import ru.astrainteractive.kapitalystik.api.mapping.WarpMapperImpl
import ru.astrainteractive.kapitalystik.database.enitity.invitation.InvitationDAO
import ru.astrainteractive.kapitalystik.database.enitity.invitation.InvitationTable
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberDAO
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberTable
import ru.astrainteractive.kapitalystik.database.enitity.org.OrgDAO
import ru.astrainteractive.kapitalystik.database.enitity.org.OrgTable
import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO

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
        val memberDAO = MemberDAO.find(expression).firstOrNull() ?: throw DBApiException.NotOrganizationMember
        memberDAO.let(memberMapper::toDTO)
    }

    override fun fetchOrg(userDTO: UserDTO): OrganizationDTO = transaction {
        val memberDTO = fetchMember(userDTO)
        fetchOrg(memberDTO)
    }

    override fun fetchOrg(memberDTO: MemberDTO): OrganizationDTO = transaction {
        val orgDAO = OrgDAO.findById(memberDTO.orgID) ?: throw DBApiException.NotOrganizationMember
        orgDAO.let(orgMapper::toDTO)
    }

    override fun fetchOrg(orgTAG: String): OrganizationDTO = transaction {
        val orgDAO = OrgDAO.find(OrgTable.tag.eq(orgTAG)).firstOrNull()
        orgDAO ?: throw DBApiException.UnexpectedException
        orgDAO.let(orgMapper::toDTO)
    }
}
