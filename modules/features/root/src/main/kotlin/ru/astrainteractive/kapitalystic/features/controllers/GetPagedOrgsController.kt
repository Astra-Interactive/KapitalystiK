package ru.astrainteractive.kapitalystic.features.controllers

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule

class GetPagedOrgsController(
    module: ClanManagementControllerModule
) : ClanManagementControllerModule by module {

    /**
     * /kpt list <page>
     */
    suspend fun getPagedOrgs(
        page: Int,
    ): List<OrganizationDTO> {
        val limit = 5
        val offset = page * limit * 1L
        return dbApi.fetchAllOrganizations(
            limit = limit,
            offset = offset
        )
    }
}
