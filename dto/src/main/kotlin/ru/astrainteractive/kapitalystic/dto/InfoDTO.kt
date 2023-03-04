package ru.astrainteractive.kapitalystic.dto

class InfoDTO(
    val id: Long,
    val orgID: Long,
    val message: String,
    val index: Int,
    val type: InfoTypeDTO
) {
    enum class InfoTypeDTO {
        RULE
    }
}
