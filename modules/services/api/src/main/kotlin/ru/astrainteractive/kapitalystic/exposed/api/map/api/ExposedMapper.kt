package ru.astrainteractive.kapitalystic.exposed.api.map.api

interface ExposedMapper<E : Any, DTO : Any> {
    fun toDTO(entity: E): DTO
}
