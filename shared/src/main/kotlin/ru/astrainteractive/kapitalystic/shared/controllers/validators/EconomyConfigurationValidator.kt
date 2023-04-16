package ru.astrainteractive.kapitalystic.shared.controllers.validators

import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.core.SharedConfiguration
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystic.shared.utils.MessageHandler

/**
 * This class will help to reduce boilerplate code in some places
 */
class EconomyConfigurationValidator(
    configuration: Dependency<SharedConfiguration>,
    economyProvider: Dependency<EconomyProvider>,
    translation: Dependency<SharedTranslation>,
    messageHandler: Dependency<MessageHandler>,
) {
    private val configuration by configuration
    private val economyProvider by economyProvider
    private val messageHandler by messageHandler
    private val translation by translation

    /**
     * Check player balance, send a message if he has not enough money
     * @return true if ok false if player has not enough money
     */
    fun validate(userDTO: UserDTO, needAmount: Number): Boolean {
        if (configuration.economy.enabled) return true
        val creationPrice = needAmount.toDouble()
        val balance = economyProvider.getBalance(userDTO.minecraftUUID) ?: 0.0
        if (balance >= creationPrice) return true
        messageHandler.sendMessage(userDTO, translation.notEnoughMoney(creationPrice.toInt()))
        return false
    }
}

