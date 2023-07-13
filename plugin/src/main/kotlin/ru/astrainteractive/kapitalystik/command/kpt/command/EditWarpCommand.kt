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
 * /kpt publicwarp <tag> <public:bool>
 */
class EditWarpCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "publicwarp"

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Warp.Visibility, translation)) return
        val tag = argument(1) { it }.validateUsage(sender, translation) ?: return

        val isPublic = argument(2) { it == "true" }.validateUsage(sender, translation) ?: return

        val sender = sender.validatePlayer(translation) ?: return

        scope.launch(dispatchers.IO) {
            clanManagementController.makeWarpPublic(
                userDTO = sender.toUserDTO(),
                isPublic = isPublic,
                warpTAG = tag
            )
        }
    }
}
