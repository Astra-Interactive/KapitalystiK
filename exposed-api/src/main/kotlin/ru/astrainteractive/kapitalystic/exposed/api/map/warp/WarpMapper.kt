package ru.astrainteractive.kapitalystic.exposed.api.map.warp

import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.WarpDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.warps.WarpsDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.ExposedMapper

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
