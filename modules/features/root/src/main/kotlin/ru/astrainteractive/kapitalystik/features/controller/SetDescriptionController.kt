package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class SetDescriptionController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {
    /**
     * /kpt rules add <index> <rule>
     */
    suspend fun setDescription(
        description: String,
        userDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        return dbApi.setDescription(
            executorDTO = userDTO,
            description = description
        )
    }
}
