package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class SetStatusController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {
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
