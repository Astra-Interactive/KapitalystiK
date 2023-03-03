package ru.astrainteractive.kapitalystic.dto

class OrganizationDTO(
    val id: Long,
    val tag: String,
    val name: String,
    val members: List<MemberDTO>,
    val leader: MemberDTO
)
