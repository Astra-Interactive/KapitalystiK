package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class CreateClanController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {
    /**
     * /kpt create <tag> <name>
     */
    suspend fun createClan(userDTO: UserDTO, tag: String, name: String): OrganizationDTO {
        val economyPrice = configuration.economy.create.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        val clan = dbApi.create(
            tag = tag,
            name = name,
            executorDTO = userDTO
        )
        economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
        return clan
    }
}
