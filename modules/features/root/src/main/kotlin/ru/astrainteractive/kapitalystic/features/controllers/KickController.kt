package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class KickController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
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
