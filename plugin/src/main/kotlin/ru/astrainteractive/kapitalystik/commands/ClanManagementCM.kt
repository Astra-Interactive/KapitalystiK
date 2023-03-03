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
import ru.astrainteractive.kapitalystic.dto.OrganizationDTO
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystik.plugin.Permissions

class ClanManagementCM(
    controller: Dependency<ClanManagementController>,
    translation: Dependency<SharedTranslation>,
    private val plugin: Plugin
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
            "setspawn" -> setSpawn()
            "spawn" -> TODO()
            "spawnpublic" -> setSpawnPublicity()
            "disband" -> disband()
            "rename" -> rename()
            "invite" -> invite()
            "accept" -> accept()
            "kick" -> kick()
            "transfer" -> transfer()
            "bio" -> setBio()
            "rules" -> rules()
            "list" -> TODO()
            "org" -> TODO()
        }
    }

    /**
     * /kpt rules
     */
    private fun Command.rules() {
        when (args.getOrNull(1)) {
            "add" -> addRule()
            "remove" -> removeRule()
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
     * /kpt setspawn
     */
    private fun Command.setSpawn() {
        if (!sender.validatePermission(Permissions.Spawn.Set)) return

        val sender = sender.validatePlayer() ?: return
        val spawnDTO = OrganizationDTO.SpawnDTO(
            x = sender.location.x,
            y = sender.location.y,
            z = sender.location.z,
        )
        PluginScope.launch(Dispatchers.IO) {
            controller.setSpawn(
                userDTO = sender.toUserDTO(),
                spawnDTO = spawnDTO
            )
        }
    }

    /**
     * /kpt spawnpublic <bool>
     */
    private fun Command.setSpawnPublicity() {
        if (!sender.validatePermission(Permissions.Spawn.Visibility)) return

        val isPublic = argument(1) { it == "true" }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.makeSpawnPublic(
                userDTO = sender.toUserDTO(),
                isPublic = isPublic,
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
            controller.setBio(
                userDTO = sender.toUserDTO(),
                bio = bio,
            )
        }
    }

    /**
     * /kpt rules add <index> <rule>
     */
    private fun Command.addRule() {
        if (!sender.validatePermission(Permissions.Management.Rules.Add)) return

        val index = argument(2) { it?.toIntOrNull() }.validateUsage(sender) ?: return
        val rule = argument(3) { it }.validateUsage(sender) ?: return

        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.setRule(
                userDTO = sender.toUserDTO(),
                rule = rule,
                index = index
            )
        }
    }

    /**
     * /kpt rules remove <index>
     */
    private fun Command.removeRule() {
        if (!sender.validatePermission(Permissions.Management.Rules.Remove)) return

        val index = argument(2) { it?.toIntOrNull() }.validateUsage(sender) ?: return
        val sender = sender.validatePlayer() ?: return

        PluginScope.launch(Dispatchers.IO) {
            controller.removeRule(
                userDTO = sender.toUserDTO(),
                index = index
            )
        }
    }
}
