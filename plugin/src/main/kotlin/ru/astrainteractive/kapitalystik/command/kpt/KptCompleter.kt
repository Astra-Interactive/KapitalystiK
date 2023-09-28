package ru.astrainteractive.kapitalystik.command.kpt

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry

object KptCompleter {
    fun KptCommandManager.completer() = plugin.registerTabCompleter("kpt") {
        val default = Bukkit.getOnlinePlayers().map(Player::getName)
        when {
            args.size == 1 -> listOf(
                "create",
                "setwarp",
                "spawn",
                "publicwarp",
                "disband",
                "rename",
                "invite",
                "accept",
                "kick",
                "transfer",
                "bio",
                "description",
                "list",
                "org"
            ).withEntry(this.args.last())

            else -> default
        }
    }
}
