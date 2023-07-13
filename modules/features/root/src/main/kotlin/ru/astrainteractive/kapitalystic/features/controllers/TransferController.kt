package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class TransferController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt transfer <user>
     */
    suspend fun transferOwnership(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        dbApi.transferOwnership(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.ownershipTransferred(userDTO)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
