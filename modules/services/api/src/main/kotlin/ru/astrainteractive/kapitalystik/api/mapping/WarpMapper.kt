package ru.astrainteractive.kapitalystik.api.mapping

import ru.astrainteractive.kapitalystik.api.mapping.api.ExposedMapper
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsDAO
import ru.astrainteractive.kapitalystik.dto.LocationDTO
import ru.astrainteractive.kapitalystik.dto.WarpDTO

internal interface WarpMapper : ExposedMapper<WarpsDAO, WarpDTO>

internal class WarpMapperImpl : WarpMapper {
    override fun toDTO(entity: WarpsDAO): WarpDTO {
        return WarpDTO(
            locationDTO = LocationDTO(
                x = entity.x,
                y = entity.y,
                z = entity.z,
                world = entity.worldName,
            ),
            orgID = entity.orgID.value,
            isPrivate = entity.isPrivate,
            tag = entity.tag
        )
    }
}
