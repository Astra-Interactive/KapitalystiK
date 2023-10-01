package ru.astrainteractive.kapitalystik.features.controllers

import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule

class MakeWarpPublicController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {
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
