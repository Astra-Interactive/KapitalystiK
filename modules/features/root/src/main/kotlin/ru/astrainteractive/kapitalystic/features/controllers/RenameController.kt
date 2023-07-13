package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class RenameController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt rename <name>
     */
    suspend fun renameClan(userDTO: UserDTO, newName: String) {
        val economyPrice = configuration.economy.rename.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.rename(
            newName = newName,
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.clanRenamed(newName)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
