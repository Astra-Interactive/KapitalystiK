@file:Suppress("Filename")

package ru.astrainteractive.kapitalystik.command.kpt.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.ArgumentResult
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.core.Translation
import ru.astrainteractive.kapitalystik.plugin.Permissions

/**
 * Converting spigot [Player] model into DTO object
 */
fun Player.toUserDTO() = UserDTO(
    minecraftName = this.name,
    minecraftUUID = this.uniqueId
)

/**
 * Validates usage of argument and sends a message to player about wrong usage
 */
fun <T> ArgumentResult<T>.validateUsage(sender: CommandSender, translation: Translation): T? {
    onFailure {
        sender.sendMessage(translation.wrongUsage)
        return null
    }
    return successOrNull()?.value
}

/**
 * Validates [CommandSender] as a [Player], send a message about error and return [Player] or null
 */
fun CommandSender.validatePlayer(translation: Translation): Player? {
    val player = this as? Player
    player ?: sendMessage(translation.notPlayer)
    return player
}

/**
 * Validates permission, send a message about lack and return either has or not permission
 */
fun CommandSender.validatePermission(permission: Permissions, translation: Translation): Boolean {
    if (permission.hasPermission(this)) return true
    sendMessage(translation.noPermission)
    return false
}
