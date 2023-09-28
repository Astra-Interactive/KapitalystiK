@file:Suppress("UnusedPrivateMember")

package ru.astrainteractive.kapitalystik.command.kpt

import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.command.AcceptCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.CreateOrgCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.DisbandCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.EditWarpCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.GetOrgListCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.InviteCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.KickCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.RenameCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.SetBioCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.SetDescriptionCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.SetWarpCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.TransferCommand
import ru.astrainteractive.kapitalystik.command.kpt.command.api.KptCommand

class KptCommandManager(
    module: CommandManagerModule
) : CommandManagerModule by module {
    val commands = buildList<KptCommand> {
        AcceptCommand(module).run(::add)
        CreateOrgCommand(module).run(::add)
        DisbandCommand(module).run(::add)
        EditWarpCommand(module).run(::add)
        GetOrgListCommand(module).run(::add)
        InviteCommand(module).run(::add)
        KickCommand(module).run(::add)
        RenameCommand(module).run(::add)
        SetBioCommand(module).run(::add)
        SetDescriptionCommand(module).run(::add)
        SetWarpCommand(module).run(::add)
        TransferCommand(module).run(::add)
    }

    fun register() = plugin.registerCommand("kpt") {
        val alias = args.getOrNull(0) ?: return@registerCommand
        val command = commands.firstOrNull { it.alias == alias }
        command?.callWithCommand(this)
    }
}
