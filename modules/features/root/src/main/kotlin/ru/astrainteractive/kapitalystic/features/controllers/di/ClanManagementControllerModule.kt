package ru.astrainteractive.kapitalystic.features.controllers.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystic.features.core.Configuration
import ru.astrainteractive.kapitalystic.features.failuremessenger.FailureMessenger
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger

interface ClanManagementControllerModule {
    val dbApi: KapitalystiKDBApi
    val economyProvider: EconomyProvider
    val configuration: Configuration
    val balanceValidation: BalanceValidation
    val failureMessenger: FailureMessenger
    val messenger: PlatformMessenger
}
