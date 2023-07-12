package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystic.features.failuremessenger.di.FailureMessengerModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

class FailureMessengerModule : FailureMessengerModule {
    private val rootModule by SpigotRootModule
    override val translation: Translation by rootModule.translation
}
