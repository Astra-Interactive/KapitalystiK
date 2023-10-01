package ru.astrainteractive.kapitalystik.features.controller.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.core.Configuration
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation

interface ClanManagementControllerContainer {
    val dbApi: KapitalystiKDBApi
    val economyProvider: EconomyProvider
    val configuration: Configuration
    val balanceValidation: BalanceValidation
}
