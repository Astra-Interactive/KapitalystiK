package ru.astrainteractive.kapitalystic.dto

class OrganizationDTO(
    val id: Long,
    val tag: String,
    val name: String,
    val members: List<MemberDTO>,
    val leader: MemberDTO,
    val rules: List<RuleDTO>,
    val spawn: SpawnDTO? = null
) {
    data class SpawnDTO(
        val x: Double,
        val y: Double,
        val z: Double
    )
}
