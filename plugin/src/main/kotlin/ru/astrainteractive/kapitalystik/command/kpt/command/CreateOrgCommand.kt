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
 * /kpt create <tag> <name>
 */
class CreateOrgCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "create"
    val controller = clanManagementControllers.createClanController.create()

    private suspend fun execute(sender: Player, tag: String, name: String) = runCatching {
        controller.createClan(
            userDTO = sender.toUserDTO(),
            tag = tag,
            name = name
        )
    }.onSuccess {
        val message = translation.clanCreated(name = name, tag = tag)
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Create, translation)) return

        val tag = argument(1) { it }.validateUsage(sender, translation) ?: return
        val name = argument(2) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(sender, tag, name)
        }
    }
}
