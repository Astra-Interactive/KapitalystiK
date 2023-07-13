package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.plugin.Permissions
/**
 * /kpt disband
 */
class DisbandCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "disband"

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Disband, translation)) return

        val playerSender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            clanManagementController.disband(
                userDTO = playerSender.toUserDTO(),
            )
        }
    }
}
