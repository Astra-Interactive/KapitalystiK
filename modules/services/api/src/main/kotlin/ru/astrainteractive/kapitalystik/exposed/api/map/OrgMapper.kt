package ru.astrainteractive.kapitalystik.exposed.api.map

import ru.astrainteractive.kapitalystik.dto.OrganizationDTO
import ru.astrainteractive.kapitalystik.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystik.exposed.api.map.api.ExposedMapper

internal interface OrgMapper : ExposedMapper<OrgDAO, OrganizationDTO>
internal class OrgMapperImpl(
    private val warpMapper: WarpMapper = WarpMapperImpl(),
    private val memberMapper: MemberMapper = MemberMapperImpl
) : OrgMapper {
    override fun toDTO(entity: OrgDAO): OrganizationDTO {
        return OrganizationDTO(
            id = entity.id.value,
            tag = entity.tag,
            name = entity.name,
            description = entity.description,
            status = entity.status,
            members = entity.members.map(memberMapper::toDTO),
            warps = entity.warps.map(warpMapper::toDTO),
            leader = entity.members.first { it.isOwner }.let(memberMapper::toDTO)
        )
    }
}
