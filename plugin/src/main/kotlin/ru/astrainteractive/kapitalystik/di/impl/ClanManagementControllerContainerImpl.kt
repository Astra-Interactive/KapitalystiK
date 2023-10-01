package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.core.Configuration
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation.Companion.BalanceValidation
import ru.astrainteractive.kapitalystik.features.controller.di.ClanManagementControllerContainer
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class ClanManagementControllerContainerImpl(rootModule: SpigotRootModule) : ClanManagementControllerContainer {
    override val dbApi: KapitalystiKDBApi by rootModule.kapitalystiKApi
    override val economyProvider: EconomyProvider by rootModule.economyProvider
    override val configuration: Configuration by rootModule.configuration
    override val balanceValidation: BalanceValidation by Provider {
        BalanceValidation(BalanceValidationModuleContainerImpl(rootModule))
    }
}
