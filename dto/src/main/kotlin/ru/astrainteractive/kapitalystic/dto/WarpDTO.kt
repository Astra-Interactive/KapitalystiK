package ru.astrainteractive.kapitalystic.dto

class WarpDTO(
    val locationDTO: LocationDTO,
    val tag: String,
    val orgID: Long,
    val isPrivate: Boolean
)
