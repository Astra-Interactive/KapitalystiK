package ru.astrainteractive.kapitalystik.command.kpt

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry

object KptCompleter {
    fun KptCommandManager.completer() = plugin.registerTabCompleter("kpt") {
        if (args.size == 1) {
            listOf(
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
        } else {
            Bukkit.getOnlinePlayers().map(Player::getName)
        }
    }
}
