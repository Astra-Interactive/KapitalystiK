package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

/**
 * Controller for common clan commands
 */
@Suppress("TooManyFunctions")
class ClanManagementController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * Handle [DBException]
     */
    private fun <T> Result<T>.handleFailure(userDTO: UserDTO): Result<T> {
        onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
        return this
    }

    /**
     * /kpt create <tag> <name>
     */
    suspend fun createClan(userDTO: UserDTO, tag: String, name: String) {
        val economyPrice = configuration.economy.create.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.create(
            tag = tag,
            name = name,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanCreated(name = name, tag = tag)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt setspawn
     */
    suspend fun setWarp(
        userDTO: UserDTO,
        locationDTO: LocationDTO,
        tag: String
    ) {
        val economyPrice = configuration.economy.spawn.set.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.setWarp(
            locationDTO = locationDTO,
            executorDTO = userDTO,
            tag = tag
        ).onSuccess {
            val message = translation.spawnSet
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt publicwarp <tag> <public:bool>
     */
    suspend fun makeWarpPublic(
        userDTO: UserDTO,
        warpTAG: String,
        isPublic: Boolean
    ) {
        dbApi.setWarpPublic(
            isPublic = isPublic,
            warpTAG = warpTAG,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.spawnPublic(isPublic)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt disband
     */
    suspend fun disband(userDTO: UserDTO) {
        dbApi.disband(
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.disbanded
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rename <name>
     */
    suspend fun renameClan(userDTO: UserDTO, newName: String) {
        val economyPrice = configuration.economy.rename.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.rename(
            newName = newName,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanRenamed(newName)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt invite <user>
     */
    suspend fun inviteToClan(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.invite.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.invite(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.userInvited(userDTO)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt accept <tag>
     */
    suspend fun acceptClanInvite(
        userDTO: UserDTO,
        clanTAG: String,
    ) {
        dbApi.acceptInvitation(
            executorDTO = userDTO,
            orgTag = clanTAG
        ).onSuccess {
            val message = translation.joinedToClan(clanTAG)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt kick <user>
     */
    suspend fun kickFromClan(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        dbApi.kickMember(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.userKicked(userDTO)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt transfer <user>
     */
    suspend fun transferOwnership(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        dbApi.transferOwnership(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.ownershipTransferred(userDTO)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt bio <message>
     */
    suspend fun setStatus(
        userDTO: UserDTO,
        status: String,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.setStatus(
            executorDTO = userDTO,
            status = status
        ).onSuccess {
            val message = translation.bioChanged
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rules add <index> <rule>
     */
    suspend fun setDescription(
        description: String,
        userDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.setDescription(
            executorDTO = userDTO,
            description = description
        ).onSuccess {
            val message = translation.ruleAdded
            messenger.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt list <page>
     */
    suspend fun getPagedOrgs(
        page: Int,
        userDTO: UserDTO
    ): Result<List<OrganizationDTO>> {
        val limit = 5
        val offset = page * limit * 1L
        return dbApi.fetchAllOrganizations(
            limit = limit,
            offset = offset
        ).handleFailure(userDTO)
    }
}
