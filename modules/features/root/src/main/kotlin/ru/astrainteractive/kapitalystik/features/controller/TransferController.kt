package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class TransferController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

    /**
     * /kpt transfer <user>
     */
    suspend fun transferOwnership(
        userDTO: UserDTO,
        initiatorDTO: UserDTO,
    ) {
        return dbApi.transferOwnership(
            userDTO = userDTO,
            executorDTO = initiatorDTO
        )
    }
}
