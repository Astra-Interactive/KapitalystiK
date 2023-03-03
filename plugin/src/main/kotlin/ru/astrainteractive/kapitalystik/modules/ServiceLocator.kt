package ru.astrainteractive.kapitalystik.modules

import CommandManager
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystik.plugin.Translation

object ServiceLocator {
    val PluginConfigModule = reloadable {
        TODO()
    }
    val TranslationModule = reloadable {
        Translation() as SharedTranslation
    }
    val commandManager = module {
        CommandManager(
            translationModule = TranslationModule,
        )
    }
}
