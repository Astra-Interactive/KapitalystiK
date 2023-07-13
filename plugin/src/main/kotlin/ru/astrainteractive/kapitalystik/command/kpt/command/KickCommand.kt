package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * /kpt kick <user>
 */
class KickCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "kick"
    val controller = clanManagementControllers.kickController.build()
    private suspend fun execute(memberDTO: UserDTO, sender: Player) = runCatching {
        controller.kickFromClan(
            userDTO = memberDTO,
            initiatorDTO = sender.toUserDTO(),
        )
    }.onSuccess {
        val message = translation.userKicked(memberDTO)
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        val hasPermission = sender.validatePermission(
            permission = Permissions.Management.Membership.KickMember,
            translation = translation
        )
        if (!hasPermission) return

        val memberDTO = argument(1) {
            it?.let(Bukkit::getPlayer)?.toUserDTO()
        }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(memberDTO, sender)
        }
    }
}
