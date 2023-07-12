package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule
import ru.astrainteractive.kapitalystic.features.core.Configuration
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

class BalanceValidationModuleModule : BalanceValidationModule {
    private val rootModule by SpigotRootModule
    override val configuration: Configuration by rootModule.configuration
    override val economyProvider: EconomyProvider by rootModule.economyProvider
    override val translation: Translation by rootModule.translation
    override val platformMessenger: PlatformMessenger by rootModule.platformMessenger
}
