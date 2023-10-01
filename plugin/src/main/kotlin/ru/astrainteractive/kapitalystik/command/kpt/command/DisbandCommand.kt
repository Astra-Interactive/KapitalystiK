package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerContainer
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * /kpt disband
 */
class DisbandCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "disband"
    val controller = clanManagementControllers.disbandController.create()
    private suspend fun execute(playerSender: Player) = runCatching {
        controller.disband(
            userDTO = playerSender.toUserDTO(),
        )
    }.onSuccess {
        val message = translation.disbanded
        playerSender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        playerSender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Management.Disband, translation)) return

        val playerSender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(playerSender)
        }
    }
}
