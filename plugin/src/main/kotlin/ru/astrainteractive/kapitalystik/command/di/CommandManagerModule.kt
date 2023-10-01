package ru.astrainteractive.kapitalystik.command.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.kapitalystik.features.core.Translation
import ru.astrainteractive.kapitalystik.features.failuremessenger.FailureMessenger
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.di.impl.ClanManagementControllers
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface CommandManagerModule {
    val clanManagementControllers: ClanManagementControllers
    val translation: Translation
    val plugin: KapitalystiK
    val scope: AsyncComponent
    val dispatchers: KotlinDispatchers
    val failureMessenger: FailureMessenger
}
