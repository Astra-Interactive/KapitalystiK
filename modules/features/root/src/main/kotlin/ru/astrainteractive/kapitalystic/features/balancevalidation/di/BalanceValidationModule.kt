package ru.astrainteractive.kapitalystic.features.balancevalidation.di

import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.economy.EconomyProvider

interface BalanceValidationModule : Module {
    val isEconomyEnabled: Boolean
    val economyProvider: EconomyProvider
}
