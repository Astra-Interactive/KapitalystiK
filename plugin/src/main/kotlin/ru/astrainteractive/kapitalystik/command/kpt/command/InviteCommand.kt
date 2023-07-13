package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * /kpt rename <name>
 */
class InviteCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "rename"
    val controller = clanManagementControllers.inviteController.build()

    private suspend fun execute(user: Player, initiator: Player) = runCatching {
        controller.inviteToClan(
            userDTO = user.toUserDTO(),
            initiatorDTO = initiator.toUserDTO(),
        )
    }.onSuccess {
        val message = translation.userInvited(user.toUserDTO())
        initiator.sendMessage(message)
    }.onFailure {
        val message = failureMessenger.asTranslationMessage(it)
        initiator.sendMessage(message)
    }

    override fun Command.call() {
        val hasPermission = sender.validatePermission(
            permission = Permissions.Management.Membership.Invite,
            translation = translation
        )
        if (!hasPermission) return

        val player = argument(1) {
            it?.let(Bukkit::getPlayer)
        }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(player, sender)
        }
    }
}
