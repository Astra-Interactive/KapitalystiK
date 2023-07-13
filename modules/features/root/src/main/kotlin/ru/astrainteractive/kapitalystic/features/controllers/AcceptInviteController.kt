package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class AcceptInviteController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt accept <tag>
     */
    suspend fun acceptClanInvite(
        userDTO: UserDTO,
        clanTAG: String,
    ) {
        dbApi.acceptInvitation(
            executorDTO = userDTO,
            orgTag = clanTAG
        ).onSuccess {
            val message = translation.joinedToClan(clanTAG)
            messenger.sendMessage(userDTO, message)
        }.onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
