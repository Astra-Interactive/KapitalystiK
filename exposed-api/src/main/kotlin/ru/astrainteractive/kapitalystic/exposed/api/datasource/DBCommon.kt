package ru.astrainteractive.kapitalystic.exposed.api.datasource

import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO

internal interface DBCommon {
    /**
     * Is user a member of any organization
     * Covers [DBException.NotOrganizationMember] and [DBException.AlreadyInOrganization]
     */
    suspend fun isMember(userDTO: UserDTO): Boolean

    /**
     * Checks if user is owner of organization
     * Covers [DBException.NotOrganizationOwner]
     */
    suspend fun isOwner(userDTO: UserDTO): Boolean

    /**
     * Checks if user invited or not
     */
    suspend fun isUserInvited(userDTO: UserDTO, orgID: Long): Boolean

    /**
     * Returns [MemberDAO]
     */
    suspend fun fetchMember(userDTO: UserDTO): MemberDAO

    /**
     * Returns [OrgDAO]
     */
    suspend fun fetchOrg(userDTO: UserDTO): OrgDAO

    /**
     * Returns [OrgDAO]
     */
    suspend fun fetchOrg(userDTO: MemberDAO): OrgDAO

    /**
     * Returns [OrgDAO]
     */
    suspend fun fetchOrg(orgTAG: String): OrgDAO
}