package ru.astrainteractive.kapitalystic.exposed.api.map

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapperImpl

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
            leader = entity.leader.let(memberMapper::toDTO),
            members = entity.members.map(memberMapper::toDTO),
            warps = entity.warps.map(warpMapper::toDTO)
        )
    }
}
