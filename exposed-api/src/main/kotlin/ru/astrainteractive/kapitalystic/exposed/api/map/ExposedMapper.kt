package ru.astrainteractive.kapitalystic.exposed.api.map

interface ExposedMapper<E : Any, DTO : Any> {
    fun toDTO(entity: E): DTO
}
