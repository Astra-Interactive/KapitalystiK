package ru.astrainteractive.kapitalystik.command.kpt.command.api

import ru.astrainteractive.astralibs.commands.Command

interface KptCommand {
    val alias: String
    fun Command.call()
    fun call(command: Command) = command.call()
}
