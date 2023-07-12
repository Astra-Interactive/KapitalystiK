package ru.astrainteractive.kapitalystic.exposed.api.map

import ru.astrainteractive.kapitalystic.dto.MemberDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.api.ExposedMapper
import java.util.*

internal interface MemberMapper : ExposedMapper<MemberDAO, MemberDTO>
internal object MemberMapperImpl : MemberMapper {
    override fun toDTO(entity: MemberDAO): MemberDTO {
        return MemberDTO(
            id = entity.id.value,
            orgID = entity.orgID.value,
            minecraftUUID = UUID.fromString(entity.minecraftUUID),
            minecraftName = entity.minecraftName,
            isOwner = entity.isOwner
        )
    }
}
