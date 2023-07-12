package ru.astrainteractive.kapitalystik

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.events.GlobalEventListener
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.menu.event.GlobalInventoryClickEvent
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

/**
 * Initial class for your plugin
 */
class KapitalystiK : JavaPlugin() {

    init {
        SpigotRootModule.plugin.initialize(this)
    }

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    @UnsafeApi
    override fun onEnable() {
        SpigotRootModule.database.value
        SpigotRootModule.commandManager
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
        SpigotRootModule.configuration.reload()
        SpigotRootModule.translation.reload()
    }
}
