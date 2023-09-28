package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation.Companion.BalanceValidation
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule
import ru.astrainteractive.kapitalystic.features.core.Configuration
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
