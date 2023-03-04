package ru.astrainteractive.kapitalystic.exposed.api.map

import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgDAO
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoTypeMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.info.InfoTypeMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpMapperImpl
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpTypeMapper
import ru.astrainteractive.kapitalystic.exposed.api.map.warp.WarpTypeMapperImpl

internal interface OrgMapper : ExposedMapper<OrgDAO, OrganizationDTO>
internal class OrgMapperImpl(
    private val warpTypeMapper: WarpTypeMapper = WarpTypeMapperImpl,
    private val warpMapper: WarpMapper = WarpMapperImpl(warpTypeMapper),
    private val infoTypeMapper: InfoTypeMapper = InfoTypeMapperImpl,
    private val infoMapper: InfoMapper = InfoMapperImpl(infoTypeMapper),
    private val memberMapper: MemberMapper = MemberMapperImpl
) : OrgMapper {
    override fun toDTO(entity: OrgDAO): OrganizationDTO {
        return OrganizationDTO(
            id = entity.id.value,
            tag = entity.tag,
            name = entity.name,
            leader = entity.leader.let(memberMapper::toDTO),
            members = entity.members.map(memberMapper::toDTO),
            infos = entity.infos.map(infoMapper::toDTO),
            warps = entity.warps.map(warpMapper::toDTO)
        )
    }
}
