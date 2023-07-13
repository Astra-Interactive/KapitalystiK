package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage
import ru.astrainteractive.kapitalystik.plugin.Permissions
/**
 * /kpt create <tag> <name>
 */
class CreateOrgCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "create"

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Create, translation)) return

        val tag = argument(1) { it }.validateUsage(sender, translation) ?: return
        val name = argument(2) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            val controller = clanManagementControllers.createClanController.build()
            controller.createClan(
                userDTO = sender.toUserDTO(),
                tag = tag,
                name = name
            )
        }
    }
}
