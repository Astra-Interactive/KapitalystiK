package ru.astrainteractive.kapitalystik.features.balancevalidation.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.klibs.kdi.Module

interface BalanceValidationContainer : Module {
    val isEconomyEnabled: Boolean
    val economyProvider: EconomyProvider
}
