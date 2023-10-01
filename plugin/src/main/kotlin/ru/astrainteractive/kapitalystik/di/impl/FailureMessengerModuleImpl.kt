package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.kapitalystik.features.core.Translation
import ru.astrainteractive.kapitalystik.features.failuremessenger.di.FailureMessengerModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.klibs.kdi.getValue

class FailureMessengerModuleImpl(rootModule: SpigotRootModule) : FailureMessengerModule {
    override val translation: Translation by rootModule.translation
}
