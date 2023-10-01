package ru.astrainteractive.kapitalystik.exposed.api

import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO

/**
 * All [KapitalystiKCommonDBApi] methods may throw [DBException]
 */
interface KapitalystiKCommonDBApi {
    /**
     * Is user a member of any organization
     * Covers [DBException.NotOrganizationMember] and [DBException.AlreadyInOrganization]
     */
    fun isMember(userDTO: UserDTO): Boolean

    /**
     * Checks if user is owner of organization
     * Covers [DBException.NotOrganizationOwner]
     */
    fun isOwner(userDTO: UserDTO): Boolean

    /**
     * @param userDTO - user model
     * @return [MemberDAO]
     */
    fun fetchMember(userDTO: UserDTO): MemberDTO

    /**
     * @param userDTO - member of organization
     * @return [OrgDAO]
     */
    fun fetchOrg(userDTO: UserDTO): OrganizationDTO

    /**
     * @param memberDTO - member of organization
     * @return [OrgDAO]
     */
    fun fetchOrg(memberDTO: MemberDTO): OrganizationDTO

    /**
     * @param orgTAG - tag of the organzation
     * @return [OrgDAO]
     */
    fun fetchOrg(orgTAG: String): OrganizationDTO

    /**
     * Checks either user invited to organization or not
     * @param userDTO - user to check
     * @param orgID - id of organization
     */
    fun isUserInvited(userDTO: UserDTO, orgID: Long): Boolean
}
