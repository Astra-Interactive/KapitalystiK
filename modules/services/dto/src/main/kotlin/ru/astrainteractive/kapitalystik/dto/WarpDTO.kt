package ru.astrainteractive.kapitalystik.dto

class WarpDTO(
    val locationDTO: LocationDTO,
    val tag: String,
    val orgID: Long,
    val isPrivate: Boolean
)
