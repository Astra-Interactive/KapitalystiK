package ru.astrainteractive.kapitalystik.commands

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.commands.Argument
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystik.plugin.Permissions

class ClanManagementCM(
    controller: Dependency<ClanManagementController>,
    translation: Dependency<SharedTranslation>,
    plugin: Plugin
) {
    private val controller by controller
    private val translation by translation

    /**
     * Converting spigot [Player] model into DTO object
     */
    private fun Player.toUserDTO() = UserDTO(
        minecraftName = this.name,
        minecraftUUID = this.uniqueId
    )

    /**
     * Validates usage of argument and sends a message to player about wrong usage
     */
    private fun <T> Argument<T>.validateUsage(sender: CommandSender): T? {
        onFailure {
            sender.sendMessage(translation.wrongUsage)
            return null
        }
        return successOrNull()?.value
    }

    /**
     * Validates [CommandSender] as a [Player], send a message about error and return [Player] or null
     */
    private fun CommandSender.validatePlayer(): Player? {
        val player = this as? Player
        player ?: sendMessage(translation.notPlayer)
        return player
    }

    /**
     * Validates permission, send a message about lack and return either has or not permission
     */
    private fun CommandSender.validatePermission(permission: Permissions): Boolean {
        if (permission.hasPermission(this)) return true
        sendMessage(translation.noPermission)
        return false
    }

    private val kpt = plugin.registerCommand("kpt") {
        when (args.getOrNull(0)) {
            "create" -> createOrganization()
            "setwarp" -> setWarp()
            "spawn" -> TODO()
            "publicwarp" -> editWarp()
            "disband" -> disband()
            "rename" -> rename()
            "invite" -> invite()
            "accept" -> accept()
            "kick" -> kick()
            "transfer" -> transfer()
            "bio" -> setDescription()
            "description" -> setDescription()
            "list" -> TODO()
            "org" -> TODO()
        }
    }

    /**
     * /kpt create <tag> <name>
     */
    private fun Command.createOrganization() {
        if (!sender.validatePermission(Permissions.Management.Create)) return

        val tag = argument(1) { it }.validateUsage(sender) ?: return
        val name = argument(2) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.createClan(
                userDTO = sender.toUserDTO(),
                tag = tag,
                name = name
            )
        }
    }

    /**
     * /kpt setwarp <tag>
     */
    private fun Command.setWarp() {
        if (!sender.validatePermission(Permissions.Warp.Set)) return
        val tag = argument(1) { it }.validateUsage(sender) ?: return
        val sender = sender.validatePlayer() ?: return
        val locationDTO = LocationDTO(
            x = sender.location.x,
            y = sender.location.y,
            z = sender.location.z,
            world = sender.world.name,
        )

        PluginScope.launch(Dispatchers.IO) {
            controller.setWarp(
                userDTO = sender.toUserDTO(),
                locationDTO = locationDTO,
                tag = tag
            )
        }
    }

    /**
     * /kpt publicwarp <tag> <public:bool>
     */
    private fun Command.editWarp() {
        if (!sender.validatePermission(Permissions.Warp.Visibility)) return
        val tag = argument(1) { it }.validateUsage(sender) ?: return

        val isPublic = argument(2) { it == "true" }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.makeWarpPublic(
                userDTO = sender.toUserDTO(),
                isPublic = isPublic,
                warpTAG = tag
            )
        }
    }

    /**
     * /kpt disband
     */
    private fun Command.disband() {
        if (!sender.validatePermission(Permissions.Management.Disband)) return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.disband(
                userDTO = sender.toUserDTO(),
            )
        }
    }

    /**
     * /kpt rename <name>
     */
    private fun Command.rename() {
        if (!sender.validatePermission(Permissions.Management.Rename)) return

        val newName = argument(1) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.renameClan(
                userDTO = sender.toUserDTO(),
                newName = newName,
            )
        }
    }

    /**
     * /kpt rename <name>
     */
    private fun Command.invite() {
        if (!sender.validatePermission(Permissions.Management.Membership.Invite)) return

        val player = argument(1) { it?.let(Bukkit::getPlayer)?.toUserDTO() }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.inviteToClan(
                userDTO = player,
                initiatorDTO = sender.toUserDTO(),
            )
        }
    }

    /**
     * /kpt accept <tag>
     */
    private fun Command.accept() {
        if (!sender.validatePermission(Permissions.Management.Membership.AcceptInvite)) return

        val clanTAG = argument(1) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.acceptClanInvite(
                userDTO = sender.toUserDTO(),
                clanTAG = clanTAG,
            )
        }
    }

    /**
     * /kpt kick <user>
     */
    private fun Command.kick() {
        if (!sender.validatePermission(Permissions.Management.Membership.KickMember)) return

        val memberDTO = argument(1) { it?.let(Bukkit::getPlayer)?.toUserDTO() }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.kickFromClan(
                userDTO = memberDTO,
                initiatorDTO = sender.toUserDTO(),
            )
        }
    }

    /**
     * /kpt transfer <user>
     */
    private fun Command.transfer() {
        if (!sender.validatePermission(Permissions.Management.Membership.KickMember)) return

        val memberDTO = argument(1) { it?.let(Bukkit::getPlayer)?.toUserDTO() }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.transferOwnership(
                userDTO = memberDTO,
                initiatorDTO = sender.toUserDTO(),
            )
        }
    }

    /**
     * /kpt bio <message>
     */
    private fun Command.setBio() {
        if (!sender.validatePermission(Permissions.Management.Bio)) return

        val bio = argument(1) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.setStatus(
                userDTO = sender.toUserDTO(),
                status = bio,
            )
        }
    }

    /**
     * /kpt description <message>
     */
    private fun Command.setDescription() {
        if (!sender.validatePermission(Permissions.Management.Bio)) return

        val description = argument(1) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.setDescription(
                userDTO = sender.toUserDTO(),
                description = description,
            )
        }
    }
}
