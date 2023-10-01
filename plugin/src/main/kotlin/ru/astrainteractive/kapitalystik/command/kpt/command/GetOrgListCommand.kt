package ru.astrainteractive.kapitalystik.command.kpt.command

import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.Command
import ru.astrainteractive.kapitalystik.command.di.CommandManagerContainer
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand
import ru.astrainteractive.kapitalystik.command.kpt.util.validatePlayer
import ru.astrainteractive.kapitalystik.command.kpt.util.validateUsage

/**
 * /kpt list <page>
 */
class GetOrgListCommand(module: CommandManagerContainer) : CommandManagerContainer by module, KptCommand {
    override val alias: String = "list"
    val controller = clanManagementControllers.getPagedOrgsController.create()
    private suspend fun execute(page: Int, sender: Player) = runCatching {
        controller.getPagedOrgs(page)
    }.onSuccess {
        it.forEach {
            Component
                .text(it.name)
                .color(TextColor.color(0xFFFFFF))
                .append(Component.newline())
                .append(Component.text("Members: ${it.members.size}"))
                .append(Component.newline())
                .append(Component.text("Leader: ${it.leader.minecraftName}"))
        }
    }.onFailure {
        it.printStackTrace()
        val message = failureMessenger.asTranslationMessage(it)
        sender.sendMessage(message)
    }

    override fun Command.call() {
        val page = argument(1) { it?.toIntOrNull() }.validateUsage(sender, translation) ?: return
        val sender = sender.validatePlayer(translation) ?: return
        scope.launch(dispatchers.IO) {
            execute(page, sender)
        }
    }
}
