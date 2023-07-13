package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

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
