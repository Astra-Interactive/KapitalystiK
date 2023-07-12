package ru.astrainteractive.kapitalystik.command

import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload(
    module: CommandManagerModule
) = module.plugin.registerCommand("kptreload") {
    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(module.translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(module.translation.reload)
    module.plugin.reloadPlugin()
    sender.sendMessage(module.translation.reloadComplete)
}
