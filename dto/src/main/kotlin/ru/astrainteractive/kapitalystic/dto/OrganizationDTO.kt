package ru.astrainteractive.kapitalystic.dto

class OrganizationDTO(
    val id: Long,
    val tag: String,
    val name: String,
    val leader: MemberDTO,
    val members: List<MemberDTO>,
    val infos: List<InfoDTO>,
    val warps: List<WarpDTO> = emptyList()
)
