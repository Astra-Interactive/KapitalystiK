package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class CreateClanController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * /kpt create <tag> <name>
     */
    suspend fun createClan(userDTO: UserDTO, tag: String, name: String) {
        val economyPrice = configuration.economy.create.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.create(
            tag = tag,
            name = name,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanCreated(name = name, tag = tag)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
