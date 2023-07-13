package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.dto.WarpDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class SetWarpController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

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
