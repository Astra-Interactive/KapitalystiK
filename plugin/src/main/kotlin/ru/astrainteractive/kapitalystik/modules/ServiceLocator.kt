package ru.astrainteractive.kapitalystik.modules

import ru.astrainteractive.astralibs.di.Reloadable
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astralibs.utils.economy.EconomyProvider
import ru.astrainteractive.astralibs.utils.economy.VaultEconomyProvider
import ru.astrainteractive.kapitalystic.exposed.api.factories.DatabaseFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystic.shared.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.shared.core.SharedConfiguration
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation
import ru.astrainteractive.kapitalystic.shared.utils.MessageHandler
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.commands.CommandManager
import ru.astrainteractive.kapitalystik.plugin.Translation
import ru.astrainteractive.kapitalystik.utils.SpigotMessageHandler
import java.io.File

object ServiceLocator {
    val PluginConfigModule: Reloadable<SharedConfiguration> = reloadable {
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
    val database = module {
        val dbFile = File(KapitalystiK.instance.dataFolder, "data.db")
        DatabaseFactory(dbFile.path).value
    }
    val kapitalystiKCommonApi = module {
        KapitalystiKCommonDBApiFactory().value
    }
    val kapitalystiKApi = module {
        val kapitalystiKCommonApi by kapitalystiKCommonApi
        KapitalystiKDBApiFactory(kapitalystiKCommonApi).value
    }
    val messageHandler = module {
        SpigotMessageHandler() as MessageHandler
    }
    val economyProvider = module {
        VaultEconomyProvider as EconomyProvider
    }
    val clanManagementController = module {
        ClanManagementController(
            messageHandler = messageHandler,
            dbApi = kapitalystiKApi,
            economyProvider = economyProvider,
            configuration = PluginConfigModule,
            translation = TranslationModule,
        )
    }
}
