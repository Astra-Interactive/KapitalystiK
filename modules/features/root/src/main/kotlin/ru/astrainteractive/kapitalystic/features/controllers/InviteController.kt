package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class InviteController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
    /**
     * /kpt invite <user>
     */
    suspend fun inviteToClan(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        val economyPrice = configuration.economy.invite.toDouble()
        if (!balanceValidation.validateAndNotify(userDTO, economyPrice)) return

        dbApi.invite(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        ).onSuccess {
            val message = translation.userInvited(userDTO)
            economyProvider.takeMoney(userDTO.minecraftUUID, economyPrice)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
