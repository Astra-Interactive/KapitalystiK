package ru.astrainteractive.kapitalystic.exposed.api.map.warp

import ru.astrainteractive.kapitalystic.dto.WarpDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.warps.WarpsDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.ExposedMapper

internal interface WarpMapper : ExposedMapper<WarpsDAO, WarpDTO>
internal class WarpMapperImpl(
    private val warpTypeMapper: WarpTypeMapper = WarpTypeMapperImpl
) : WarpMapper {
    override fun toDTO(entity: WarpsDAO): WarpDTO {
        return WarpDTO(
            x = entity.x,
            y = entity.y,
            z = entity.z,
            world = entity.worldName,
            type = entity.type.let(warpTypeMapper::toDTO),
            orgID = entity.orgID.value,
            isPrivate = entity.isPrivate,
            tag = entity.tag
        )
    }
}
