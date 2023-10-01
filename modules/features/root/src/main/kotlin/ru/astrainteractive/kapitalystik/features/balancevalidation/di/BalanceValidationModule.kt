package ru.astrainteractive.kapitalystik.features.balancevalidation.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.klibs.kdi.Module

interface BalanceValidationModule : Module {
    val isEconomyEnabled: Boolean
    val economyProvider: EconomyProvider
}
