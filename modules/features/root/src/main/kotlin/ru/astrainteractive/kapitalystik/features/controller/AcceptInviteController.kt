package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class AcceptInviteController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

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
