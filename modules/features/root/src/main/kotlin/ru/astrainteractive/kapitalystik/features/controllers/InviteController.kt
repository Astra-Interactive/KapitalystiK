package ru.astrainteractive.kapitalystik.features.controllers

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule

class InviteController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * /kpt invite <user>
     */
    suspend fun inviteToClan(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.invite.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        val result = dbApi.invite(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        )
        economyProvider.takeMoney(initiatorDTO.minecraftUUID, economyPrice)
        return result
    }
}
