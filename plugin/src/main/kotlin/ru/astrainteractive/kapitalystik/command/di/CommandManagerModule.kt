package ru.astrainteractive.kapitalystik.command.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KotlinDispatchers
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystik.KapitalystiK
import ru.astrainteractive.kapitalystik.di.impl.ClanManagementControllers

interface CommandManagerModule {
    val clanManagementControllers: ClanManagementControllers
    val translation: Translation
    val plugin: KapitalystiK
    val scope: AsyncComponent
    val dispatchers: KotlinDispatchers
}
