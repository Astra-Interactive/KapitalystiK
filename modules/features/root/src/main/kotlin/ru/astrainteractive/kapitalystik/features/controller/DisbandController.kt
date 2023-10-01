package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class DisbandController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

    /**
     * /kpt disband
     */
    suspend fun disband(userDTO: UserDTO) {
        return dbApi.disband(
            executorDTO = userDTO
        )
    }
}
