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
 * /kpt description <message>
 */
class SetDescriptionCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "description"

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Bio, translation)) return

        val description = argument(1) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            val controller = clanManagementControllers.setDescriptionController.build()
            controller.setDescription(
                userDTO = sender.toUserDTO(),
                description = description,
            )
        }
    }
}
