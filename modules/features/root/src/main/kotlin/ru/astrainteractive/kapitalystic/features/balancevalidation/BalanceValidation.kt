package ru.astrainteractive.kapitalystic.features.balancevalidation

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.DBException
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule

interface BalanceValidation {
    /**
     * Check player balance
     * @return true if ok false if player has not enough money
     */
    fun haveAtLeast(userDTO: UserDTO, requiredAmount: Number): Boolean

    /**
     * Check player balance
     * @throws [DBException.NotEnoughMoney] if not enough money
     */
    fun assertHaveAtLeast(userDTO: UserDTO, requiredAmount: Number)

    companion object {
        fun BalanceValidation(module: BalanceValidationModule): BalanceValidation = BalanceValidationComponent(module)
    }
}
