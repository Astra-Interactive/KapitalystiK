package ru.astrainteractive.kapitalystik.commands

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.kapitalystic.shared.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation

/**
 * Command handler for your plugin
 * It's better to create different executors for different commands
 * @see Reload
 */
class CommandManager(
    cmcModule: Dependency<ClanManagementController>,
    translationModule: Dependency<SharedTranslation>,
    plugin: Plugin
) {
    /**
     * Here you should declare commands for your plugin
     *
     * Commands stored in plugin.yml
     *
     * etemp has TabCompleter
     */
    init {
        reload(translationModule)
        ClanManagementCM(
            cmcModule,
            translationModule,
            plugin
        )
    }
}
