package ru.astrainteractive.kapitalystic.api

import ru.astrainteractive.kapitalystic.dto.*

/**
 * All [KapitalystiKDBApi] methods may throw [DBException]
 */
interface KapitalystiKDBApi {
    /**
     * Create an organization
     * @param tag - Organization tag
     * @param name - Organization name
     * @param executorDTO - Organization creator
     * @return Organization ID Wrapped with Result
     */
    suspend fun create(tag: String, name: String, executorDTO: UserDTO): Result<OrganizationDTO>

    /**
     * Set status of organization
     */
    suspend fun setStatus(status: String, executorDTO: UserDTO): Result<*>

    /**
     * Set status of organization
     */
    suspend fun setDescription(description: String, executorDTO: UserDTO): Result<*>

    /**
     * Set spawn public access
     * @param isPublic - is spawn public
     * @param warpTAG - tag of selected warp
     * @param executorDTO - org creator
     */
    suspend fun setWarpPublic(isPublic: Boolean, warpTAG: String, executorDTO: UserDTO): Result<*>

    /**
     * Disband clan
     * @param executorDTO - organization owner
     */
    suspend fun disband(executorDTO: UserDTO): Result<*>

    /**
     * Rename organization
     * @param newName - new organization name
     * @param executorDTO - organization owner
     */
    suspend fun rename(newName: String, executorDTO: UserDTO): Result<*>

    /**
     * Invite user to organization
     * @param userDTO - user which will be invited
     * @param executorDTO - command initiator - organization owner
     */
    suspend fun invite(userDTO: UserDTO, executorDTO: UserDTO): Result<*>

    /**
     * Accept invitation
     * @param executorDTO - invited user
     * @param orgTag - tag of invited clan
     */
    suspend fun acceptInvitation(executorDTO: UserDTO, orgTag: String): Result<MemberDTO>

    /**
     * Kick member from organization
     * @param userDTO - user which will be kicked
     * @param executorDTO - command initiator - owner of organization
     */
    suspend fun kickMember(userDTO: UserDTO, executorDTO: UserDTO): Result<*>

    /**
     * Transfer ownership of organization to another player
     * @param userDTO - User which will be new head of organization
     * @param executorDTO - command initiator - owner of organization
     */
    suspend fun transferOwnership(userDTO: UserDTO, executorDTO: UserDTO): Result<*>

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
    suspend fun fetchUserOrganization(executorDTO: UserDTO): Result<OrganizationDTO>

    /**
     * Set organization warp
     */
    suspend fun setWarp(
        locationDTO: LocationDTO,
        executorDTO: UserDTO,
        tag: String
    ): Result<WarpDTO>
}
