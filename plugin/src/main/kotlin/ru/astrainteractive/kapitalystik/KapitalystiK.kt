package ru.astrainteractive.kapitalystik

import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.event.GlobalEventListener
import ru.astrainteractive.astralibs.menu.event.GlobalInventoryClickEvent
import ru.astrainteractive.kapitalystik.database.di.factory.DatabaseFactory
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

/**
 * Initial class for your plugin
 */
class KapitalystiK : JavaPlugin() {
    private val rootModule = SpigotRootModule()

    init {
        rootModule.plugin.initialize(this)
    }

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    @UnsafeApi
    override fun onEnable() {
        runBlocking {
            DatabaseFactory.createSchema(rootModule.database.value)
        }
        rootModule.commandManager.create()
        GlobalEventListener.onEnable(this)
        GlobalInventoryClickEvent.onEnable(this)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    @UnsafeApi
    override fun onDisable() {
        HandlerList.unregisterAll(this)
        GlobalEventListener.onDisable()
        GlobalInventoryClickEvent.onDisable()
        PluginScope.close()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        rootModule.configuration.reload()
        rootModule.translation.reload()
    }
}
