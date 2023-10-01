package ru.astrainteractive.kapitalystik.api.mapping.api

internal interface ExposedMapper<E : Any, DTO : Any> {
    fun toDTO(entity: E): DTO
}
