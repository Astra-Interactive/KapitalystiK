package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystik.features.balancevalidation.di.BalanceValidationModule
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class BalanceValidationModuleModuleImpl(rootModule: SpigotRootModule) : BalanceValidationModule {
    override val isEconomyEnabled: Boolean by Provider {
        rootModule.configuration.value.economy.enabled
    }
    override val economyProvider: EconomyProvider by rootModule.economyProvider
}
