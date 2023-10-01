package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.kapitalystik.features.core.Translation
import ru.astrainteractive.kapitalystik.features.failuremessenger.FailureMessenger
import ru.astrainteractive.kapitalystik.features.failuremessenger.FailureMessenger.Companion.FailureMessenger
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.command.di.CommandManagerModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

class CommandManagerModuleImpl(rootModule: SpigotRootModule) : CommandManagerModule {
    override val translation: Translation by rootModule.translation
    override val plugin: KapitalystiK by rootModule.plugin
    override val scope: AsyncComponent by rootModule.scope
    override val dispatchers: KotlinDispatchers by rootModule.dispatchers
    override val failureMessenger: FailureMessenger = FailureMessenger(FailureMessengerModuleImpl(rootModule))
    override val clanManagementControllers: ClanManagementControllers = ClanManagementControllers(
        ClanManagementControllerModuleImpl(rootModule)
    )
}
