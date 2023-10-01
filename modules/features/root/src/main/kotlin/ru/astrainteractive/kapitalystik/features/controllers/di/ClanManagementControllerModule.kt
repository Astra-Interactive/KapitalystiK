package ru.astrainteractive.kapitalystik.features.controllers.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystik.features.core.Configuration

interface ClanManagementControllerModule {
    val dbApi: KapitalystiKDBApi
    val economyProvider: EconomyProvider
    val configuration: Configuration
    val balanceValidation: BalanceValidation
}
