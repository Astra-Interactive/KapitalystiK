package ru.astrainteractive.kapitalystik.dto

class OrganizationDTO(
    val id: Long,
    val tag: String,
    val name: String,
    val status: String,
    val description: String,
    val leader: MemberDTO,
    val members: List<MemberDTO>,
    val warps: List<WarpDTO> = emptyList()
)
