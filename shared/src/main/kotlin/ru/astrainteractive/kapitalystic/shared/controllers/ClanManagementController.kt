package ru.astrainteractive.kapitalystic.shared.controllers

import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.controllers.validators.EconomyConfigurationValidator
import ru.astrainteractive.kapitalystic.shared.core.SharedConfiguration
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystic.shared.utils.FailureHandler
import ru.astrainteractive.kapitalystic.shared.utils.MessageHandler

/**
 * Controller for common clan commands
 */
class ClanManagementController(
    messageHandler: Dependency<MessageHandler>,
    dbApi: Dependency<KapitalystiKDBApi>,
    economyProvider: Dependency<EconomyProvider>,
    configuration: Dependency<SharedConfiguration>,
    translation: Dependency<SharedTranslation>,
    extensions: Dependency<FailureHandler>
) {
    private val messageHandler by messageHandler
    private val dbApi by dbApi
    private val economyProvider by economyProvider
    private val configuration by configuration
    private val translation by translation
    private val extension by extensions
    private val economyConfigurationValidator = EconomyConfigurationValidator(
        configuration = configuration,
        economyProvider = economyProvider,
        translation = translation,
        messageHandler = messageHandler
    )

    /**
     * Handle [DBException]
     */
    private fun <T> Result<T>.handleFailure(userDTO: UserDTO): Result<T> {
        onFailure {
            val message = extension.asTranslationMessage(it)
            messageHandler.sendMessage(userDTO, message)
        }
        return this
    }

    /**
     * /kpt create <tag> <name>
     */
    suspend fun createClan(userDTO: UserDTO, tag: String, name: String) {
        val economyPrice = configuration.economy.create.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.create(
            tag = tag,
            name = name,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanCreated(name = name, tag = tag)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
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
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setWarp(
            locationDTO = locationDTO,
            executorDTO = userDTO,
            tag = tag
        ).onSuccess {
            val message = translation.spawnSet
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt spawnpublic <bool>
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
            messageHandler.sendMessage(userDTO, message)
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
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rename <name>
     */
    suspend fun renameClan(userDTO: UserDTO, newName: String) {
        val economyPrice = configuration.economy.rename.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.rename(
            newName = newName,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanRenamed(newName)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
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
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.invite(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.userInvited(userDTO)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
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
            messageHandler.sendMessage(userDTO, message)
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
            messageHandler.sendMessage(userDTO, message)
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
            messageHandler.sendMessage(userDTO, message)
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
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setStatus(
            executorDTO = userDTO,
            status = status
        ).onSuccess {
            val message = translation.bioChanged
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rules add <index> <rule>
     */
    suspend fun setDescription(
        description: String,
        userDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.rules.add.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setDescription(
            executorDTO = userDTO,
            description = description
        ).onSuccess {
            val message = translation.ruleAdded
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }
}
