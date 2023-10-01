package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class KickController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {
    /**
     * /kpt kick <user>
     */
    suspend fun kickFromClan(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        return dbApi.kickMember(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        )
    }
}
