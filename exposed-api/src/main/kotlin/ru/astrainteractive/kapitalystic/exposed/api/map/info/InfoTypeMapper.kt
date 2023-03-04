package ru.astrainteractive.kapitalystic.exposed.api.map.info

import ru.astrainteractive.astralibs.domain.mapping.Mapper
import ru.astrainteractive.kapitalystic.dto.InfoDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.info.InfoType

internal interface InfoTypeMapper : Mapper<InfoType, InfoDTO.InfoTypeDTO>
internal object InfoTypeMapperImpl : InfoTypeMapper {
    override fun fromDTO(
        it: InfoDTO.InfoTypeDTO
    ): InfoType = when (it) {
        InfoDTO.InfoTypeDTO.RULE -> InfoType.Rule
    }

    override fun toDTO(
        it: InfoType
    ): InfoDTO.InfoTypeDTO = when (it) {
        InfoType.Rule -> InfoDTO.InfoTypeDTO.RULE
    }
}
