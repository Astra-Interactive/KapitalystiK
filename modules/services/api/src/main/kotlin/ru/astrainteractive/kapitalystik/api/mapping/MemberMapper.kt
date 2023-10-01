package ru.astrainteractive.kapitalystik.api.mapping

import ru.astrainteractive.kapitalystik.api.mapping.api.ExposedMapper
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberDAO
import ru.astrainteractive.kapitalystik.dto.MemberDTO
import java.util.UUID

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
