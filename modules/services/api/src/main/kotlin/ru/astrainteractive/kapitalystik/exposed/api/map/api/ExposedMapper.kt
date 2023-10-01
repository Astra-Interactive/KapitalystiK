package ru.astrainteractive.kapitalystik.exposed.api.map.api

interface ExposedMapper<E : Any, DTO : Any> {
    fun toDTO(entity: E): DTO
}
