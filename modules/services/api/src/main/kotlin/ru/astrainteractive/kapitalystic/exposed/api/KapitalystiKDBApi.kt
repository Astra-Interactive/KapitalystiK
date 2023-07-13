package ru.astrainteractive.kapitalystic.exposed.api

import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.MemberDTO
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.dto.WarpDTO

/**
 * All [KapitalystiKDBApi] methods may throw [DBException]
 */
@Suppress("TooManyFunctions")
interface KapitalystiKDBApi {
    /**
     * Create an organization
     * @param tag - Organization tag
     * @param name - Organization name
     * @param executorDTO - Organization creator
     * @return Organization ID Wrapped with Result
     */
    suspend fun create(tag: String, name: String, executorDTO: UserDTO): OrganizationDTO

    /**
     * Set status of organization
     */
    suspend fun setStatus(status: String, executorDTO: UserDTO)

    /**
     * Set status of organization
     */
    suspend fun setDescription(description: String, executorDTO: UserDTO)

    /**
     * Set spawn public access
     * @param isPublic - is spawn public
     * @param warpTAG - tag of selected warp
     * @param executorDTO - org creator
     */
    suspend fun setWarpPublic(isPublic: Boolean, warpTAG: String, executorDTO: UserDTO)

    /**
     * Disband clan
     * @param executorDTO - organization owner
     */
    suspend fun disband(executorDTO: UserDTO)

    /**
     * Rename organization
     * @param newName - new organization name
     * @param executorDTO - organization owner
     */
    suspend fun rename(newName: String, executorDTO: UserDTO)

    /**
     * Invite user to organization
     * @param userDTO - user which will be invited
     * @param executorDTO - command initiator - organization owner
     */
    suspend fun invite(userDTO: UserDTO, executorDTO: UserDTO)

    /**
     * Accept invitation
     * @param executorDTO - invited user
     * @param orgTag - tag of invited clan
     */
    suspend fun acceptInvitation(executorDTO: UserDTO, orgTag: String): MemberDTO

    /**
     * Kick member from organization
     * @param userDTO - user which will be kicked
     * @param executorDTO - command initiator - owner of organization
     */
    suspend fun kickMember(userDTO: UserDTO, executorDTO: UserDTO)

    /**
     * Transfer ownership of organization to another player
     * @param userDTO - User which will be new head of organization
     * @param executorDTO - command initiator - owner of organization
     */
    suspend fun transferOwnership(userDTO: UserDTO, executorDTO: UserDTO)

    /**
     * Fetch list of all organizations
     * @param limit - limit amount. If equals -1 - all organizations will be retrieved
     * @param offset offset according to limit
     */
    suspend fun fetchAllOrganizations(limit: Int, offset: Long): List<OrganizationDTO>

    /**
     * Fetch organization by its id
     */
    suspend fun fetchOrganization(id: Long): OrganizationDTO

    /**
     * Fetch organization by its tag
     */
    suspend fun fetchOrganization(tag: String): OrganizationDTO

    /**
     * Fetch user organization
     */
    suspend fun fetchUserOrganization(executorDTO: UserDTO): OrganizationDTO

    /**
     * Set organization warp
     */
    suspend fun setWarp(
        locationDTO: LocationDTO,
        executorDTO: UserDTO,
        tag: String
    ): WarpDTO
}
