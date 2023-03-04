package ru.astrainteractive.kapitalystic.exposed.api.map.warp

import ru.astrainteractive.astralibs.domain.mapping.Mapper
import ru.astrainteractive.kapitalystic.dto.WarpDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.warps.WarpType

internal interface WarpTypeMapper : Mapper<WarpType, WarpDTO.WarpTypeDTO>
internal object WarpTypeMapperImpl : WarpTypeMapper {
    override fun fromDTO(
        it: WarpDTO.WarpTypeDTO
    ): WarpType = when (it) {
        WarpDTO.WarpTypeDTO.SPAWN -> WarpType.SPAWN
        WarpDTO.WarpTypeDTO.CUSTOM -> WarpType.CUSTOM
    }

    override fun toDTO(
        it: WarpType
    ): WarpDTO.WarpTypeDTO = when (it) {
        WarpType.SPAWN -> WarpDTO.WarpTypeDTO.SPAWN
        WarpType.CUSTOM -> WarpDTO.WarpTypeDTO.CUSTOM
    }
}
