package ru.astrainteractive.kapitalystik.command

import ru.astrainteractive.kapitalystik.command.di.CommandManagerContainer
import ru.astrainteractive.kapitalystik.command.kpt.KptCommandManager

class CommandManager(
    module: CommandManagerContainer
) : CommandManagerContainer by module {
    init {
        reload()
        KptCommandManager(module).register()
    }
}
