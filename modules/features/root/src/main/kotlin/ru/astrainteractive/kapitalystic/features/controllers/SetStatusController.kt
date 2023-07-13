package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class SetStatusController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * /kpt bio <message>
     */
    suspend fun setStatus(
        userDTO: UserDTO,
        status: String,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        val result = dbApi.setStatus(
            executorDTO = userDTO,
            status = status
        )
        economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
        return result
    }
}
