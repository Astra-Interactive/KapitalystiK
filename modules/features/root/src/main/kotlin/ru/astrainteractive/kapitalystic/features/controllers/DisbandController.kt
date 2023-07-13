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
        return dbApi.disband(
            executorDTO = userDTO
        )
    }
}
