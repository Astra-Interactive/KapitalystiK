package ru.astrainteractive.kapitalystic.shared.controllers

import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.controllers.validators.EconomyConfigurationValidator
import ru.astrainteractive.kapitalystic.shared.core.SharedConfiguration
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystic.shared.utils.FailureHandler
import ru.astrainteractive.kapitalystic.shared.utils.MessageHandler

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
            user = userDTO
        ).onSuccess {
            val message = translation.clanCreated(name = name, tag = tag)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt setspawn
     */
    suspend fun setSpawn(userDTO: UserDTO, spawnDTO: OrganizationDTO.SpawnDTO) {
        val economyPrice = configuration.economy.spawn.set.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setSpawn(
            spawnDTO = spawnDTO,
            userDTO = userDTO
        ).onSuccess {
            val message = translation.spawnSet
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt spawnpublic <bool>
     */
    suspend fun makeSpawnPublic(userDTO: UserDTO, isPublic: Boolean) {
        dbApi.setSpawnPublic(
            isPublic = isPublic,
            user = userDTO
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
            user = userDTO
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
            user = userDTO
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
            user = userDTO,
            initiator = initiatorDTO
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
            user = userDTO,
            clanTAG = clanTAG
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
            user = userDTO,
            initiator = initiatorDTO
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
            user = userDTO,
            initiator = initiatorDTO
        ).onSuccess {
            val message = translation.ownershipTransferred(userDTO)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt bio <message>
     */
    suspend fun setBio(
        userDTO: UserDTO,
        bio: String,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setBio(
            user = userDTO,
            bio = bio
        ).onSuccess {
            val message = translation.bioChanged
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rules add <index> <rule>
     */
    suspend fun setRule(
        rule: String,
        index: Int,
        userDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.rules.add.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.setRule(
            userDTO = userDTO,
            index = index,
            rule = rule
        ).onSuccess {
            val message = translation.ruleAdded
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }

    /**
     * /kpt rules remove <index>
     */
    suspend fun removeRule(
        rule: String,
        index: Int,
        userDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.rules.remove.toDouble()
        if (!economyConfigurationValidator.validate(userDTO, economyPrice)) return

        dbApi.removeRule(
            userDTO = userDTO,
            index = index,
            rule = rule
        ).onSuccess {
            val message = translation.ruleAdded
            messageHandler.sendMessage(userDTO, message)
        }.handleFailure(userDTO)
    }
}
