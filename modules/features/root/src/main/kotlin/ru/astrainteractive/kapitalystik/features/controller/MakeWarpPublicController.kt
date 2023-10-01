package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class MakeWarpPublicController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {
    /**
     * /kpt publicwarp <tag> <public:bool>
     */
    suspend fun makeWarpPublic(
        userDTO: UserDTO,
        warpTAG: String,
        isPublic: Boolean
    ) {
        return dbApi.setWarpPublic(
            isPublic = isPublic,
            warpTAG = warpTAG,
            executorDTO = userDTO
        )
    }
}
