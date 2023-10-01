package ru.astrainteractive.kapitalystik.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.economy.AnyEconomyProvider
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystik.command.CommandManager
import ru.astrainteractive.kapitalystik.core.Configuration
import ru.astrainteractive.kapitalystik.database.di.factory.DatabaseFactory
import ru.astrainteractive.kapitalystik.di.impl.CommandManagerContainerImpl
import ru.astrainteractive.kapitalystik.features.platformmessenger.PlatformMessenger
import ru.astrainteractive.kapitalystik.plugin.SpigotTranslation
import ru.astrainteractive.kapitalystik.util.SpigotPlatformMessenger
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers
import java.io.File

class SpigotRootModule : Module {
    val plugin = Lateinit<KapitalystiK>()
    val dispatchers = Single {
        DefaultKotlinDispatchers
    }

    val scope = Single<AsyncComponent> {
        AsyncComponent.Default()
    }
    val configuration = Reloadable {
        val plugin by plugin
        val configFile: FileManager = DefaultSpigotFileManager(
            plugin,
            "config.yml",
        )
        ConfigLoader().toClassOrDefault<Configuration>(configFile.configFile, ::Configuration)
    }
    val translation = Reloadable {
        val plugin by plugin
        SpigotTranslation(plugin)
    }
    val commandManager = Factory {
        CommandManager(CommandManagerContainerImpl(this))
    }
    val database = Single {
        val plugin by plugin
        val dbFile = File(plugin.dataFolder, "kapitalystic.db")
        if (!dbFile.exists()) dbFile.parentFile.mkdirs()
        DatabaseFactory(dbFile.path).create()
    }
    val kapitalystiKCommonApi = Single {
        KapitalystiKCommonDBApiFactory().create()
    }
    val kapitalystiKApi = Single {
        val kapitalystiKCommonApi by kapitalystiKCommonApi
        KapitalystiKDBApiFactory(kapitalystiKCommonApi).create()
    }
    val platformMessenger = Single {
        SpigotPlatformMessenger() as PlatformMessenger
    }
    val economyProvider = Single {
        AnyEconomyProvider(plugin.value)
    }
}
