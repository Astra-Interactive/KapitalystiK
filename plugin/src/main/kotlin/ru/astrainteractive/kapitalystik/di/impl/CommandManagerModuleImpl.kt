package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KotlinDispatchers
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.features.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

class CommandManagerModuleImpl : CommandManagerModule {
    private val rootModule by SpigotRootModule
    override val clanManagementController: ClanManagementController by rootModule.clanManagementController
    override val translation: Translation by rootModule.translation
    override val plugin: KapitalystiK by rootModule.plugin
    override val scope: AsyncComponent by rootModule.scope
    override val dispatchers: KotlinDispatchers by rootModule.dispatchers
}
