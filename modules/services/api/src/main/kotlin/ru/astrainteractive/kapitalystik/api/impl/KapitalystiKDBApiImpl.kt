package ru.astrainteractive.kapitalystik.api.impl

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
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
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsDAO
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsTable
import ru.astrainteractive.kapitalystik.dto.LocationDTO
import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.dto.WarpDTO

@Suppress("TooManyFunctions")
internal class KapitalystiKDBApiImpl(
    private val dbCommon: KapitalystiKCommonDBApi,
    private val warpMapper: WarpMapper = WarpMapperImpl(),
    private val memberMapper: MemberMapper = MemberMapperImpl,
    private val orgMapper: OrgMapper = OrgMapperImpl(
        warpMapper = warpMapper,
        memberMapper = memberMapper,
    )
) : KapitalystiKDBApi {

    private fun OrganizationDTO.toDAO(): OrgDAO = transaction {
        OrgDAO.findById(this@toDAO.id) ?: throw DBApiException.UnexpectedException
    }

    private fun MemberDTO.toDAO(): MemberDAO = transaction {
        MemberDAO.findById(this@toDAO.id) ?: throw DBApiException.UnexpectedException
    }

    override suspend fun create(
        tag: String,
        name: String,
        executorDTO: UserDTO
    ): OrganizationDTO = transaction {
        if (MemberDAO.find(MemberTable.minecraftUUID eq executorDTO.minecraftUUID.toString()).firstOrNull() != null) {
            throw DBApiException.AlreadyInOrganization
        }
        if (OrgDAO.find(OrgTable.tag eq tag).firstOrNull() != null) throw DBApiException.OrgAlreadyExists
        if (OrgDAO.find(OrgTable.name eq name).firstOrNull() != null) throw DBApiException.OrgAlreadyExists
        val orgDAO = OrgDAO.new org@{
            this.tag = tag
            this.name = name
            this.description = ""
            this.status = ""
        }
        MemberDAO.new {
            this.minecraftUUID = executorDTO.minecraftUUID.toString()
            this.minecraftName = executorDTO.minecraftName
            this.orgID = orgDAO.id
            this.isOwner = true
        }
        OrgDAO.findById(orgDAO.id)?.let(orgMapper::toDTO) ?: throw DBApiException.UnexpectedException
    }

    override suspend fun setStatus(
        status: String,
        executorDTO: UserDTO
    ) {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        transaction {
            val org = dbCommon.fetchOrg(executorDTO).toDAO()
            org.status = status
        }
    }

    override suspend fun setDescription(
        description: String,
        executorDTO: UserDTO
    ) {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        transaction {
            val org = dbCommon.fetchOrg(executorDTO).toDAO()
            org.description = description
        }
    }

    override suspend fun setWarpPublic(
        isPublic: Boolean,
        warpTAG: String,
        executorDTO: UserDTO
    ) {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        val member = dbCommon.fetchMember(executorDTO)
        val warp = WarpsTable.tag.eq(warpTAG).and(WarpsTable.orgID.eq(member.orgID)).let {
            WarpsDAO.find(it)
        }.firstOrNull() ?: throw DBApiException.UnexpectedException
        warp.isPrivate = !isPublic
    }

    override suspend fun disband(
        executorDTO: UserDTO
    ) {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        transaction {
            val org = runBlocking { dbCommon.fetchOrg(executorDTO).toDAO() }
            MemberTable.deleteWhere {
                MemberTable.orgID.eq(org.id)
            }
            WarpsTable.deleteWhere {
                WarpsTable.orgID.eq(org.id)
            }
            InvitationTable.deleteWhere {
                InvitationTable.orgID.eq(org.id)
            }
            org.delete()
        }
    }

    override suspend fun rename(
        newName: String,
        executorDTO: UserDTO
    ) {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        transaction {
            val org = dbCommon.fetchOrg(executorDTO).toDAO()
            org.name = newName
        }
    }

    override suspend fun invite(
        userDTO: UserDTO,
        executorDTO: UserDTO
    ) {
        transaction {
            if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
            val member = dbCommon.fetchMember(executorDTO)
            val orgID = member.orgID
            if (dbCommon.isUserInvited(userDTO, orgID)) throw DBApiException.AlreadyInvited
            InvitationDAO.new {
                this.minecraftName = userDTO.minecraftName
                this.minecraftUUID = userDTO.minecraftUUID.toString()
                this.orgID = EntityID(orgID, OrgTable)
            }
        }
    }

    override suspend fun acceptInvitation(
        executorDTO: UserDTO,
        orgTag: String
    ): MemberDTO {
        return transaction {
            if (dbCommon.isMember(executorDTO)) throw DBApiException.AlreadyInOrganization
            val org = dbCommon.fetchOrg(orgTag).toDAO()
            MemberDAO.new {
                this.minecraftName = executorDTO.minecraftName
                this.minecraftUUID = executorDTO.minecraftUUID.toString()
                this.orgID = org.id
                this.isOwner = false
            }.let(memberMapper::toDTO)
        }
    }

    override suspend fun kickMember(
        userDTO: UserDTO,
        executorDTO: UserDTO
    ) {
        transaction {
            if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
            if (!dbCommon.isMember(userDTO)) throw DBApiException.NotOrganizationMember
            dbCommon.fetchMember(userDTO).toDAO().delete()
        }
    }

    override suspend fun transferOwnership(
        userDTO: UserDTO,
        executorDTO: UserDTO
    ) {
        transaction {
            if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
            if (!dbCommon.isMember(userDTO)) throw DBApiException.NotOrganizationMember
            val ownerDAO = dbCommon.fetchMember(executorDTO)
            val newOwnerDao = dbCommon.fetchMember(userDTO).toDAO()
            MemberDAO.findById(newOwnerDao.id)?.apply {
                this.isOwner = true
            }
            MemberDAO.findById(ownerDAO.id)?.apply {
                this.isOwner = false
            }
        }
    }

    override suspend fun fetchAllOrganizations(
        limit: Int,
        offset: Long
    ): List<OrganizationDTO> {
        val query = OrgDAO.all().apply {
            if (limit != -1) {
                limit(limit, offset)
            }
        }
        return query.map(orgMapper::toDTO)
    }

    override suspend fun fetchOrganization(
        id: Long
    ): OrganizationDTO {
        val org = OrgDAO.findById(id)?.let(orgMapper::toDTO)
        return org ?: throw DBApiException.UnexpectedException
    }

    override suspend fun fetchOrganization(
        tag: String
    ): OrganizationDTO {
        return transaction {
            val org = OrgDAO.find(OrgTable.tag.eq(tag)).firstOrNull()
            org?.let(orgMapper::toDTO) ?: throw DBApiException.UnexpectedException
        }
    }

    override suspend fun fetchUserOrganization(
        executorDTO: UserDTO
    ): OrganizationDTO = dbCommon.fetchOrg(executorDTO)

    override suspend fun setWarp(
        locationDTO: LocationDTO,
        executorDTO: UserDTO,
        tag: String
    ): WarpDTO {
        if (!dbCommon.isOwner(executorDTO)) throw DBApiException.NotOrganizationOwner
        val member = dbCommon.fetchMember(executorDTO).toDAO()
        return WarpsDAO.new {
            this.orgID = member.orgID
            this.tag = tag
            this.x = locationDTO.x
            this.y = locationDTO.y
            this.z = locationDTO.z
            this.worldName = locationDTO.world
            this.isPrivate = true
        }.let(warpMapper::toDTO)
    }
}
