package ru.astrainteractive.kapitalystik.exposed.api.map

import ru.astrainteractive.kapitalystik.dto.LocationDTO
import ru.astrainteractive.kapitalystik.dto.WarpDTO
import ru.astrainteractive.kapitalystik.exposed.api.enitites.warps.WarpsDAO
import ru.astrainteractive.kapitalystik.exposed.api.map.api.ExposedMapper

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
