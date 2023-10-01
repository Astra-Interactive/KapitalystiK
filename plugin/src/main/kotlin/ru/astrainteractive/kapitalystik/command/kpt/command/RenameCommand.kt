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
 * /kpt rename <name>
 */
class RenameCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "rename"
    val controller = clanManagementControllers.renameController.create()

    private suspend fun execute(sender: Player, newName: String) = runCatching {
        controller.renameClan(
            userDTO = sender.toUserDTO(),
            newName = newName,
        )
    }.onSuccess {
        val message = translation.clanRenamed(newName)
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Rename, translation)) return

        val newName = argument(1) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(sender, newName)
        }
    }
}
