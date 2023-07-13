package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class CreateClanController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
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
