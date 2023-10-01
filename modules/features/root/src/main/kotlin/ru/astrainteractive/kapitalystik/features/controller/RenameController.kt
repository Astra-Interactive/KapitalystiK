package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class RenameController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

    /**
     * /kpt rename <name>
     */
    suspend fun renameClan(userDTO: UserDTO, newName: String) {
        val economyPrice = configuration.economy.rename.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        val result = dbApi.rename(
            newName = newName,
            executorDTO = userDTO
        )
        economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
        return result
    }
}
