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
 * /kpt bio <message>
 */
class SetBioCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "bio"
    val controller = clanManagementControllers.setStatusController.create()

    private suspend fun execute(sender: Player, bio: String) = runCatching {
        controller.setStatus(
            userDTO = sender.toUserDTO(),
            status = bio,
        )
    }.onSuccess {
        val message = translation.bioChanged
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Bio, translation)) return

        val bio = argument(1) { it }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(sender, bio)
        }
    }
}
