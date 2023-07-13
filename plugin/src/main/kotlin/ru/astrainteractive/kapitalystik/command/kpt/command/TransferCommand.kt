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
 * /kpt transfer <user>
 */
class TransferCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "transfer"
    val controller = clanManagementControllers.transferController.build()

    private suspend fun execute(member: Player, sender: Player) = runCatching {
        controller.transferOwnership(
            userDTO = member.toUserDTO(),
            initiatorDTO = sender.toUserDTO(),
        )
    }.onSuccess {
        val message = translation.ownershipTransferred(member.toUserDTO())
        sender.sendMessage(message)
    }.onFailure {
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        val hasPermission = sender.validatePermission(
            permission = Permissions.Management.Membership.TransferOwnership,
            translation = translation
        )
        if (!hasPermission) return

        val member = argument(1) {
            it?.let(Bukkit::getPlayer)
        }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(member, sender)
        }
    }
}
