package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class SetStatusController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * /kpt bio <message>
     */
    suspend fun setStatus(
        userDTO: UserDTO,
        status: String,
    ) {
        val economyPrice = configuration.economy.bio.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.setStatus(
            executorDTO = userDTO,
            status = status
        ).onSuccess {
            val message = translation.bioChanged
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
