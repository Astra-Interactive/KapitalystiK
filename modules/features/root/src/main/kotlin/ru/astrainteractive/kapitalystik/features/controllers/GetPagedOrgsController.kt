package ru.astrainteractive.kapitalystik.features.controllers

import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule

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
