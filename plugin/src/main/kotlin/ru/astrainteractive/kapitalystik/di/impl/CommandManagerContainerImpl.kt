package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.command.di.CommandManagerContainer
import ru.astrainteractive.kapitalystik.core.Translation
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.kapitalystik.features.failuremessenger.FailureMessenger
import ru.astrainteractive.kapitalystik.features.failuremessenger.FailureMessenger.Companion.FailureMessenger
import ru.astrainteractive.klibs.kdi.getValue
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

class CommandManagerContainerImpl(rootModule: SpigotRootModule) : CommandManagerContainer {
    override val translation: Translation by rootModule.translation
    override val plugin: KapitalystiK by rootModule.plugin
    override val scope: AsyncComponent by rootModule.scope
    override val dispatchers: KotlinDispatchers by rootModule.dispatchers
    override val failureMessenger: FailureMessenger = FailureMessenger(FailureMessengerContainerImpl(rootModule))
    override val clanManagementControllers: ClanManagementControllers = ClanManagementControllers(
        ClanManagementControllerContainerImpl(rootModule)
    )
}
