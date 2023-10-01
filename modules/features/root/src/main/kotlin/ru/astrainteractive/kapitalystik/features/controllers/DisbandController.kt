package ru.astrainteractive.kapitalystik.features.controllers

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule

class DisbandController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt disband
     */
    suspend fun disband(userDTO: UserDTO) {
        return dbApi.disband(
            executorDTO = userDTO
        )
    }
}
