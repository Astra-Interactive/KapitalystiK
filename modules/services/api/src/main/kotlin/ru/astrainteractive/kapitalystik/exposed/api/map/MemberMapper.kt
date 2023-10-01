package ru.astrainteractive.kapitalystik.exposed.api.map

import ru.astrainteractive.kapitalystik.dto.MemberDTO
import ru.astrainteractive.kapitalystik.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystik.exposed.api.map.api.ExposedMapper
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
