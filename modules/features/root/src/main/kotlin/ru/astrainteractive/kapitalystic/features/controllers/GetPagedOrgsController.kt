package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class GetPagedOrgsController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt list <page>
     */
    suspend fun getPagedOrgs(
        page: Int,
        userDTO: UserDTO
    ): Result<List<OrganizationDTO>> {
        val limit = 5
        val offset = page * limit * 1L
        return dbApi.fetchAllOrganizations(
            limit = limit,
            offset = offset
        ).onFailure {
            val message = failureMessenger.asTranslationMessage(it)
            messenger.sendMessage(userDTO, message)
        }
    }
}
