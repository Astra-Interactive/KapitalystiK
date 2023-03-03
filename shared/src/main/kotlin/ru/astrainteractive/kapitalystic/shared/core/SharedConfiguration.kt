package ru.astrainteractive.kapitalystic.shared.core
/**
 * Shared configuration interface to use it on both Spigot and other platforms
 */
interface SharedConfiguration {
    val economy: Economy

    interface Economy {
        val isEnabled: Boolean
        val create: Int
        val rename: Int
        val invite: Int
        val bio: Int
        val spawn: Spawn
        val rules: Rules

        interface Spawn {
            val set: Int
            val visibility: Int
        }

        interface Rules {
            val add: Int
            val remove: Int
        }
    }
}
