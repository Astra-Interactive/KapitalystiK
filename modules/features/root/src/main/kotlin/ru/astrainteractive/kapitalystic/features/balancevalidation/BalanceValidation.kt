package ru.astrainteractive.kapitalystic.features.balancevalidation

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule

interface BalanceValidation {
    /**
     * Check player balance, send a message if he has not enough money
     * @return true if ok false if player has not enough money
     */
    fun validateAndNotify(userDTO: UserDTO, requiredAmount: Number): Boolean

    companion object {
        fun BalanceValidation(module: BalanceValidationModule): BalanceValidation = BalanceValidationComponent(module)
    }
}
