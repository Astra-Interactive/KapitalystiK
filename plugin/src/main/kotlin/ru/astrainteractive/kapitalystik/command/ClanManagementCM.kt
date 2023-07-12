@file:Suppress("UnusedPrivateMember")

package ru.astrainteractive.kapitalystik.command

import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.Argument
import ru.astrainteractive.astralibs.commands.Command
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry
import ru.astrainteractive.kapitalystic.dto.LocationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.plugin.Permissions

@Suppress("TooManyFunctions")
class ClanManagementCM(
    module: CommandManagerModule
) : CommandManagerModule by module {

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

    private val kptArgs = plugin.registerTabCompleter("kpt") {
        if (args.size == 1) {
            listOf(
                "create",
                "setwarp",
                "spawn",
                "publicwarp",
                "disband",
                "rename",
                "invite",
                "accept",
                "kick",
                "transfer",
                "bio",
                "description",
                "list",
                "org"
            ).withEntry(this.args.last())
        } else {
            Bukkit.getOnlinePlayers().map(Player::getName)
        }
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
            "list" -> getOrgList()
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

        scope.launch(dispatchers.IO) {
            clanManagementController.createClan(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.setWarp(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.makeWarpPublic(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.disband(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.renameClan(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.inviteToClan(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.acceptClanInvite(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.kickFromClan(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.transferOwnership(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.setStatus(
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

        scope.launch(dispatchers.IO) {
            clanManagementController.setDescription(
                userDTO = sender.toUserDTO(),
                description = description,
            )
        }
    }

    /**
     * /kpt list <page>
     */
    private fun Command.getOrgList() {
        val page = argument(1) { it?.toIntOrNull() }.validateUsage(sender) ?: return
        val sender = sender.validatePlayer() ?: return
        scope.launch(dispatchers.IO) {
            clanManagementController.getPagedOrgs(page, sender.toUserDTO()).onSuccess {
                it.forEach {
                    Component
                        .text(it.name)
                        .color(TextColor.color(0xFFFFFF))
                        .append(Component.newline())
                        .append(Component.text("Members: ${it.members.size}"))
                        .append(Component.newline())
                        .append(Component.text("Leader: ${it.leader.minecraftName}"))
                }
            }
        }
    }
}
