package ru.astrainteractive.kapitalystic.dto

class WarpDTO(
    val x: Double,
    val y: Double,
    val z: Double,
    val type: WarpTypeDTO,
    val world: String,
    val orgID: Long,
    val isPrivate: Boolean
) {
    enum class WarpTypeDTO {
        SPAWN, CUSTOM
    }
}
