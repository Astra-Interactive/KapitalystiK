package ru.astrainteractive.kapitalystic.exposed.api.map.info

import ru.astrainteractive.kapitalystic.dto.InfoDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.info.InfoDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.ExposedMapper

internal interface InfoMapper : ExposedMapper<InfoDAO, InfoDTO>
internal class InfoMapperImpl(
    private val infoTypeMapper: InfoTypeMapper = InfoTypeMapperImpl
) : InfoMapper {
    override fun toDTO(entity: InfoDAO): InfoDTO {
        return InfoDTO(
            id = entity.id.value,
            orgID = entity.orgID.value,
            index = entity.index,
            type = entity.type.let(infoTypeMapper::toDTO),
            message = entity.message
        )
    }
}
