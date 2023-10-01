package ru.astrainteractive.kapitalystik.features.core

import kotlinx.serialization.Serializable

/**
 * Shared configuration interface to use it on both Spigot and other platforms
 */
@Serializable
data class Configuration(
    val economy: Economy = Economy()
) {
    @Serializable
    data class Economy(
        val enabled: Boolean = false,
        val create: Int = 10000,
        val rename: Int = 500,
        val invite: Int = 0,
        val bio: Int = 0,
        val spawn: Spawn = Spawn(),
    ) {
        @Serializable
        data class Spawn(
            val set: Int = 0,
            val visibility: Int = 0
        )
    }
}
