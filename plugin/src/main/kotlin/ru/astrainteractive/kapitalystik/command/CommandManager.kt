package ru.astrainteractive.kapitalystik.command

import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.command.kpt.KptCommandManager

class CommandManager(
    module: CommandManagerModule
) : CommandManagerModule by module {
    init {
        reload()
        KptCommandManager(module).register()
    }
}
