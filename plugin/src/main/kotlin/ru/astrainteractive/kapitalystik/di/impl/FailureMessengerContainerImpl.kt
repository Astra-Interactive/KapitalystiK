package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.kapitalystik.core.Translation
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.kapitalystik.features.failuremessenger.di.FailureMessengerContainer
import ru.astrainteractive.klibs.kdi.getValue

class FailureMessengerContainerImpl(rootModule: SpigotRootModule) : FailureMessengerContainer {
    override val translation: Translation by rootModule.translation
}
