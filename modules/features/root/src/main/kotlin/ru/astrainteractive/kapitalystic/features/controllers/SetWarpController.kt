package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
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
    ) {
        val economyPrice = configuration.economy.spawn.set.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.setWarp(
            locationDTO = locationDTO,
            executorDTO = userDTO,
            tag = tag
        ).onSuccess {
            val message = translation.spawnSet
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
