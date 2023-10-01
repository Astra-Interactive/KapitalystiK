package ru.astrainteractive.kapitalystik.features.balancevalidation

import ru.astrainteractive.kapitalystik.api.exception.DBApiException
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.balancevalidation.di.BalanceValidationContainer

/**
 * This class will help to reduce boilerplate code in some places
 */
internal class BalanceValidationComponent(
    container: BalanceValidationContainer
) : BalanceValidation, BalanceValidationContainer by container {

    override fun haveAtLeast(userDTO: UserDTO, requiredAmount: Number): Boolean {
        if (!isEconomyEnabled) return true
        val creationPrice = requiredAmount.toDouble()
        val balance = economyProvider.getBalance(userDTO.minecraftUUID) ?: 0.0
        if (balance >= creationPrice) return true
        return false
    }

    override fun assertHaveAtLeast(userDTO: UserDTO, requiredAmount: Number) {
        if (!haveAtLeast(userDTO, requiredAmount)) throw DBApiException.NotEnoughMoney(requiredAmount)
    }
}
