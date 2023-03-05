package ru.astrainteractive.kapitalystic.api

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.dto.WarpDTO

/**
 * All [KapitalystiKDBApi] methods may throw [DBException]
 */
interface KapitalystiKDBApi {
    /**
     * Create an organization
     * @param tag - Organization tag
     * @param name - Organization name
     * @param user - Organization creator
     * @return Organization ID Wrapped with Result
     */
    suspend fun create(tag: String, name: String, user: UserDTO): Result<OrganizationDTO>

    /**
     * Set spawn public access
     * @param isPublic - is spawn public
     * @param warpTAG - tag of selected warp
     * @param user - org creator
     */
    suspend fun setWarpPublic(isPublic: Boolean, warpTAG: String, user: UserDTO): Result<*>

    /**
     * Disband clan
     * @param user - organization owner
     */
    suspend fun disband(user: UserDTO): Result<*>

    /**
     * Rename organization
     * @param newName - new organization name
     * @param user - organization owner
     */
    suspend fun rename(newName: String, user: UserDTO): Result<*>

    /**
     * Invite user to organization
     * @param user - user which will be invited
     * @param initiator - command initiator - organization owner
     */
    suspend fun invite(user: UserDTO, initiator: UserDTO): Result<*>

    /**
     * Accept invitation
     * @param user - invited user
     * @param clanTAG - tag of invited clan
     */
    suspend fun acceptInvitation(user: UserDTO, clanTAG: String): Result<*>

    /**
     * Kick member from organization
     * @param user - user which will be kicked
     * @param initiator - command initiator - owner of organization
     */
    suspend fun kickMember(user: UserDTO, initiator: UserDTO): Result<*>

    /**
     * Transfer ownership of organization to another player
     * @param user - User which will be new head of organization
     * @param initiator - command initiator - owner of organization
     */
    suspend fun transferOwnership(user: UserDTO, initiator: UserDTO): Result<*>

    /**
     * Set bio of current organization
     * @param bio - bio of organization
     * @param user - owner of organization
     */
    suspend fun setBio(bio: String, user: UserDTO): Result<*>

    /**
     * Fetch list of all organizations
     */
    suspend fun fetchAllOrganizations(): Result<List<OrganizationDTO>>

    /**
     * Fetch organization by its id
     */
    suspend fun fetchOrganization(id: Long): Result<OrganizationDTO>

    /**
     * Fetch organization by its tag
     */
    suspend fun fetchOrganization(tag: String): Result<OrganizationDTO>

    /**
     * Fetch user organization
     */
    suspend fun fetchUserOrganization(userDTO: UserDTO): Result<List<OrganizationDTO>>

    /**
     * Add organization rule to index [index]
     */
    suspend fun setRule(rule: String, index: Int, userDTO: UserDTO): Result<*>

    /**
     * Remove rule from [index]
     */
    suspend fun removeRule(index: Int, userDTO: UserDTO): Result<*>

    /**
     * Set organization warp
     */
    suspend fun setWarp(
        warpDTO: WarpDTO,
        userDTO: UserDTO
    ): Result<*>
}
