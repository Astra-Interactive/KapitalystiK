package ru.astrainteractive.kapitalystic.features.balancevalidation.di

import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.features.core.Configuration
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger

interface BalanceValidationModule : Module {
    val configuration: Configuration
    val economyProvider: EconomyProvider
    val translation: Translation
    val platformMessenger: PlatformMessenger
}
