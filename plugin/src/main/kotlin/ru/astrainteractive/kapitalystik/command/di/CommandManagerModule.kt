package ru.astrainteractive.kapitalystik.command.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.KotlinDispatchers
import ru.astrainteractive.kapitalystic.features.controllers.ClanManagementController
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystik.KapitalystiK

interface CommandManagerModule {
    val clanManagementController: ClanManagementController
    val translation: Translation
    val plugin: KapitalystiK
    val scope: AsyncComponent
    val dispatchers: KotlinDispatchers
}
