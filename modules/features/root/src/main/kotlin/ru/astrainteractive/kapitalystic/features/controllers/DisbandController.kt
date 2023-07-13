package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class DisbandController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt disband
     */
    suspend fun disband(userDTO: UserDTO) {
        dbApi.disband(
            executorDTO = userDTO
        ).onSuccess {
            val message = translation.disbanded
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
