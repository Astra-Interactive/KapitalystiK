package ru.astrainteractive.kapitalystic.shared

interface SharedConfiguration {
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
