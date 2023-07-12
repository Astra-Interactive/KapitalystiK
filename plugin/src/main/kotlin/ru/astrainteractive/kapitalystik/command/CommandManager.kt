package ru.astrainteractive.kapitalystik.command

import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager(
    module: CommandManagerModule
) {
    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        reload(module)
        ClanManagementCM(module)
    }
}
