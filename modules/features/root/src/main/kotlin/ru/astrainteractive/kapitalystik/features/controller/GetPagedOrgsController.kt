package ru.astrainteractive.kapitalystik.features.controller

import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer

class GetPagedOrgsController(
    container: ClanManagementControllerContainer
) : ClanManagementControllerContainer by container {

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
