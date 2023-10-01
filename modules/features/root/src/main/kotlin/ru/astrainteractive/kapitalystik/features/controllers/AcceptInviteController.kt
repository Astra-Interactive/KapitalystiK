package ru.astrainteractive.kapitalystik.features.controllers

import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule

class AcceptInviteController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt accept <tag>
     */
    suspend fun acceptClanInvite(
        userDTO: UserDTO,
        clanTAG: String,
    ): MemberDTO {
        return dbApi.acceptInvitation(
            executorDTO = userDTO,
            orgTag = clanTAG
        )
    }
}
