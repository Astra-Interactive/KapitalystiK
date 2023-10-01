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
 * /kpt publicwarp <tag> <public:bool>
 */
class EditWarpCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "publicwarp"
    val controller = clanManagementControllers.makeWarpPublicController.create()

    private suspend fun execute(sender: Player, isPublic: Boolean, tag: String) = runCatching {
        controller.makeWarpPublic(
            userDTO = sender.toUserDTO(),
            isPublic = isPublic,
            warpTAG = tag
        )
    }.onSuccess {
        val message = translation.spawnPublic(isPublic)
        sender.sendMessage(message)
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Warp.Visibility, translation)) return
        val tag = argument(1) { it }.validateUsage(sender, translation) ?: return

        val isPublic = argument(2) { it == "true" }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            execute(sender, isPublic, tag)
        }
    }
}
