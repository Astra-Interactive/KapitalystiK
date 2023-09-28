package ru.astrainteractive.kapitalystik.command

import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.kapitalystik.plugin.Permissions

fun CommandManager.reload() = plugin.registerCommand("kptreload") {
    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.reload)
    plugin.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
