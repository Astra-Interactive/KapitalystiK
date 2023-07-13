package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.toUserDTO
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePermission
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * /kpt setwarp <tag>
 */
class SetWarpCommand(module: CommandManagerModule) : CommandManagerModule by module, KptCommand {
    override val alias: String = "setwarp"
    val controller = clanManagementControllers.setWarpController.build()

    private suspend fun execute(sender: Player, locationDTO: LocationDTO, tag: String) = runCatching {
        controller.setWarp(
            userDTO = sender.toUserDTO(),
            locationDTO = locationDTO,
            tag = tag
        )
    }.onSuccess {
        val message = translation.spawnSet
        sender.sendMessage(message)
    }.onFailure {
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        if (!sender.validatePermission(Permissions.Warp.Set, translation)) return
        val tag = argument(1) { it }.validateUsage(sender, translation) ?: return
        val sender = sender.validatePlayer(translation) ?: return
        val locationDTO = LocationDTO(
            x = sender.location.x,
            y = sender.location.y,
            z = sender.location.z,
            world = sender.world.name,
        )

        scope.launch(dispatchers.IO) {
            execute(sender, locationDTO, tag)
        }
    }
}
