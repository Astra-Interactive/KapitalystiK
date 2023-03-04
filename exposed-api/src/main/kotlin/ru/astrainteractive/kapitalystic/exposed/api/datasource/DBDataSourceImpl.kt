package ru.astrainteractive.kapitalystic.exposed.api.datasource

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable

internal class DBDataSourceImpl : DBDataSource {
    override suspend fun isMember(userDTO: UserDTO): Boolean {
        val expression = MemberTable.minecraftUUID.eq(userDTO.minecraftUUID.toString())
        return !MemberDAO.find(expression).empty()
    }

    override suspend fun isOwner(userDTO: UserDTO): Boolean {
        val member = MemberTable.minecraftUUID
            .eq(userDTO.minecraftUUID.toString())
            .let { expression ->
                MemberDAO.find(expression).firstOrNull()
            } ?: throw DBException.NotOrganizationMember

        val org = OrgTable.id.eq(member.orgID).let {
            OrgDAO.find(it)
        }.firstOrNull() ?: throw DBException.NotOrganizationOwner
        return org.leader.minecraftUUID == member.minecraftUUID
    }

    override suspend fun isUserInvited(userDTO: UserDTO, orgID: Long): Boolean {
        return !InvitationTable.minecraftUUID
            .eq(userDTO.minecraftUUID.toString())
            .and(InvitationTable.orgID.eq(orgID)).let {
                InvitationDAO.find(it)
            }.empty()
    }

    override suspend fun fetchMember(userDTO: UserDTO): MemberDAO {
        val member = MemberTable.minecraftUUID
            .eq(userDTO.minecraftUUID.toString())
            .let { expression ->
                MemberDAO.find(expression).firstOrNull()
            } ?: throw DBException.NotOrganizationMember
        return member
    }
}
