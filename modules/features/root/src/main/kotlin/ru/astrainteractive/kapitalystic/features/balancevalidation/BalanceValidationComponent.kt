package ru.astrainteractive.kapitalystic.features.balancevalidation

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule

/**
 * This class will help to reduce boilerplate code in some places
 */
internal class BalanceValidationComponent(
    module: BalanceValidationModule
) : BalanceValidation, BalanceValidationModule by module {

    /**
     * Check player balance, send a message if he has not enough money
     * @return true if ok false if player has not enough money
     */
    override fun validateAndNotify(userDTO: UserDTO, requiredAmount: Number): Boolean {
        if (configuration.economy.enabled) return true
        val creationPrice = requiredAmount.toDouble()
        val balance = economyProvider.getBalance(userDTO.minecraftUUID) ?: 0.0
        if (balance >= creationPrice) return true
        platformMessenger.sendMessage(userDTO, translation.notEnoughMoney(creationPrice.toInt()))
        return false
    }
}
