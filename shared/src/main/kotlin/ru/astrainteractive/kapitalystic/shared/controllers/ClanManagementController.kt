package ru.astrainteractive.kapitalystic.shared.controllers

import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.api.KapitalystiKDBApi
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

    suspend fun createClan(userDTO: UserDTO, tag: String, name: String) {
        if (!economyConfigurationValidator.validate(userDTO, configuration.economy.create)) return

        dbApi.create(
            tag = tag,
            name = name,
            user = userDTO
        ).onSuccess {
            val message = translation.clanCreated(name = name, tag = tag)
            economyProvider.takeMoney(userDTO.minecraftUUID, configuration.economy.create.toDouble())
            messageHandler.sendMessage(userDTO, message)
        }.onFailure {
            val message = extension.asTranslationMessage(it)
            messageHandler.sendMessage(userDTO, message)
        }
    }

    suspend fun renameClan(userDTO: UserDTO, newName: String) {
        if (!economyConfigurationValidator.validate(userDTO, configuration.economy.rename)) return

        dbApi.rename(
            newName = newName,
            user = userDTO
        ).onSuccess {
            val message = translation.clanRenamed(newName)
            messageHandler.sendMessage(userDTO, message)
        }.onFailure {
            val message = extension.asTranslationMessage(it)
            messageHandler.sendMessage(userDTO, message)
        }
    }
}
