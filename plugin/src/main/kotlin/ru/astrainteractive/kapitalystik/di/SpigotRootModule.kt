package ru.astrainteractive.kapitalystik.di

import ru.astrainteractive.astralibs.Lateinit
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.economy.VaultEconomyProvider
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.exposed.api.factories.DatabaseFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystic.features.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.features.core.Configuration
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.command.CommandManager
import ru.astrainteractive.kapitalystik.di.impl.ClanManagementControllerModule
import ru.astrainteractive.kapitalystik.di.impl.CommandManagerModuleImpl
import ru.astrainteractive.kapitalystik.plugin.SpigotTranslation
import ru.astrainteractive.kapitalystik.util.DefaultAsyncComponent
import ru.astrainteractive.kapitalystik.util.SpigotPlatformMessenger
import java.io.File

object SpigotRootModule : Module {
    val plugin = Lateinit<KapitalystiK>()
    val dispatchers = Single {
        KDispatchers
    }

    val scope = Single<AsyncComponent> {
        DefaultAsyncComponent()
    }
    val configuration = Reloadable {
        val plugin by plugin
        val configFile: FileManager = DefaultSpigotFileManager(
            plugin,
            "config.yml",
        )
        ConfigLoader.toClassOrDefault<Configuration>(configFile.configFile, ::Configuration)
    }
    val translation = Reloadable {
        val plugin by plugin
        SpigotTranslation(plugin)
    }
    val commandManager = Single {
        val plugin by plugin
        CommandManager(CommandManagerModuleImpl())
    }
    val database = Single {
        val plugin by plugin
        val dbFile = File(plugin.dataFolder, "kapitalystic.db")
        DatabaseFactory(dbFile.path).build()
    }
    val kapitalystiKCommonApi = Single {
        KapitalystiKCommonDBApiFactory().build()
    }
    val kapitalystiKApi = Single {
        val kapitalystiKCommonApi by kapitalystiKCommonApi
        KapitalystiKDBApiFactory(kapitalystiKCommonApi).build()
    }
    val platformMessenger = Single {
        SpigotPlatformMessenger() as PlatformMessenger
    }
    val economyProvider = Single {
        VaultEconomyProvider()
    }
    val clanManagementController = Single {
        ClanManagementController(ClanManagementControllerModule())
    }
}
