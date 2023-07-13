package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

class BalanceValidationModuleModuleImpl : BalanceValidationModule {
    private val rootModule by SpigotRootModule
    override val isEconomyEnabled: Boolean
        get() = rootModule.configuration.value.economy.enabled
    override val economyProvider: EconomyProvider by rootModule.economyProvider
}
