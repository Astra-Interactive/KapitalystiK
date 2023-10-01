package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation.Companion.BalanceValidation
import ru.astrainteractive.kapitalystik.features.controllers.di.ClanManagementControllerModule
import ru.astrainteractive.kapitalystik.features.core.Configuration
import ru.astrainteractive.kapitalystik.di.SpigotRootModule
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class ClanManagementControllerModuleImpl(rootModule: SpigotRootModule) : ClanManagementControllerModule {
    override val dbApi: KapitalystiKDBApi by rootModule.kapitalystiKApi
    override val economyProvider: EconomyProvider by rootModule.economyProvider
    override val configuration: Configuration by rootModule.configuration
    override val balanceValidation: BalanceValidation by Provider {
        BalanceValidation(BalanceValidationModuleModuleImpl(rootModule))
    }
}
