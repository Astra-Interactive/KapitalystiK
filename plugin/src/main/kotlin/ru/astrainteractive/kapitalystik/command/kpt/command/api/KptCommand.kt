package ru.astrainteractive.kapitalystik.command.kpt.command.api

import ru.astrainteractive.astralibs.command.Command

interface KptCommand {
    val alias: String
    fun Command.call()

    fun callWithCommand(command: Command) = command.call()
}
