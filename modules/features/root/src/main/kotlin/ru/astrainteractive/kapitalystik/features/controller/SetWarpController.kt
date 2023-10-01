package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.LocationDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.dto.WarpDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class SetWarpController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

    /**
     * /kpt setspawn
     */
    suspend fun setWarp(
        userDTO: UserDTO,
        locationDTO: LocationDTO,
        tag: String
    ): WarpDTO {
        val economyPrice = configuration.economy.spawn.set.toDouble()
        balanceValidation.assertHaveAtLeast(userDTO, economyPrice)

        val result = dbApi.setWarp(
            locationDTO = locationDTO,
            executorDTO = userDTO,
            tag = tag
        )
        economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
        return result
    }
}
