package ru.astrainteractive.kapitalystik.commands

import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystik.KapitalystiK
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
    translationModule: Dependency<SharedTranslation>
) = KapitalystiK.instance.registerCommand("kptreload") {
    val translation by translationModule
    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.reload)
    KapitalystiK.instance.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
