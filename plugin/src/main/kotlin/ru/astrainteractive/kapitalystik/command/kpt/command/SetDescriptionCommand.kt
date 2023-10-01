package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerContainer
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * /kpt description <message>
 */
class SetDescriptionCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "description"
    val controller = clanManagementControllers.setDescriptionController.create()
    private suspend fun execute(sender: Player, description: String) = runCatching {
        controller.setDescription(
            userDTO = sender.toUserDTO(),
            description = description,
        )
    }.onSuccess {
        val message = translation.ruleAdded
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Bio, translation)) return

        val description = argument(1) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(sender, description)
        }
    }
}
