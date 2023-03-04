package ru.astrainteractive.kapitalystic.exposed.api

import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.dto.WarpDTO
import ru.astrainteractive.kapitalystic.exposed.api.datasource.DBDataSource
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.MemberMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.MemberMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.OrgMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.OrgMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoTypeMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoTypeMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpTypeMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpTypeMapperImpl

internal class KapitalystiKDBApiImpl(

    private val warpTypeMapper: WarpTypeMapper = WarpTypeMapperImpl,
    private val warpMapper: WarpMapper = WarpMapperImpl(warpTypeMapper),
    private val infoTypeMapper: InfoTypeMapper = InfoTypeMapperImpl,
    private val infoMapper: InfoMapper = InfoMapperImpl(infoTypeMapper),
    private val memberMapper: MemberMapper = MemberMapperImpl,
    private val orgMapper: OrgMapper = OrgMapperImpl(
        warpTypeMapper = warpTypeMapper,
        warpMapper = warpMapper,
        infoTypeMapper = infoTypeMapper,
        infoMapper = infoMapper,
        memberMapper = memberMapper,
    ),
    private val dbDataSource: DBDataSource
) : KapitalystiKDBApi {
    override suspend fun create(
        tag: String,
        name: String,
        user: UserDTO
    ): Result<OrganizationDTO> = kotlin.runCatching {
        if (dbDataSource.isMember(user)) throw DBException.AlreadyInOrganization
        transaction {
            OrgDAO.new org@{
                val orgID = this.id
                this.tag = tag
                this.name = name
                this.leader = MemberDAO.new {
                    this.minecraftUUID = user.minecraftUUID.toString()
                    this.minecraftName = user.minecraftName
                    this.orgID = orgID
                }
            }
        }.let(orgMapper::toDTO)
    }

    override suspend fun setSpawnPublic(isPublic: Boolean, user: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(user)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun disband(user: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(user)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun rename(newName: String, user: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(user)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun invite(user: UserDTO, initiator: UserDTO): Result<*> = kotlin.runCatching {
        if (!dbDataSource.isOwner(user)) throw DBException.NotOrganizationOwner
        val member = dbDataSource.fetchMember(initiator)
        val orgID = member.orgID
        if (dbDataSource.isUserInvited(user, orgID.value)) throw DBException.AlreadyInvited
        InvitationDAO.new {
            this.minecraftName = user.minecraftName
            this.minecraftUUID = user.minecraftUUID.toString()
            this.orgID = orgID
        }
    }

    override suspend fun acceptInvitation(user: UserDTO, clanTAG: String): Result<*> {
        if (dbDataSource.isMember(user)) throw DBException.AlreadyInOrganization
        TODO("Not yet implemented")
    }

    override suspend fun kickMember(user: UserDTO, initiator: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(initiator)) throw DBException.NotOrganizationOwner
        if (dbDataSource.isMember(user)) throw DBException.AlreadyInOrganization
        TODO("Not yet implemented")
    }

    override suspend fun transferOwnership(user: UserDTO, initiator: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(initiator)) throw DBException.NotOrganizationOwner
        if (dbDataSource.isMember(user)) throw DBException.AlreadyInOrganization
        TODO("Not yet implemented")
    }

    override suspend fun setBio(bio: String, user: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(user)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun fetchAllOrganizations(): Result<List<OrganizationDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchOrganization(id: Long): Result<OrganizationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchOrganization(tag: String): Result<OrganizationDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchUserOrganization(userDTO: UserDTO): Result<List<OrganizationDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun setRule(rule: String, index: Int, userDTO: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(userDTO)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun removeRule(index: Int, userDTO: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(userDTO)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }

    override suspend fun setWarp(warpDTO: WarpDTO, userDTO: UserDTO): Result<*> {
        if (!dbDataSource.isOwner(userDTO)) throw DBException.NotOrganizationOwner
        TODO("Not yet implemented")
    }
}
