package ru.astrainteractive.kapitalystic.features.controllers.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.economy.InMemoryEconomyProvider
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule
import ru.astrainteractive.kapitalystic.features.core.Configuration

class TestClanManagementControllerModule(
    val isEconomyEnabled: Boolean = true,
    override val economyProvider: EconomyProvider = InMemoryEconomyProvider(),
    override val configuration: Configuration = Configuration()
) : ClanManagementControllerModule {
    private val commonDBApi: KapitalystiKCommonDBApi = KapitalystiKCommonDBApiFactory().build()
    override val dbApi: KapitalystiKDBApi = KapitalystiKDBApiFactory(commonDBApi).build()
    private val balanceValidationModule = object : BalanceValidationModule {
        override val isEconomyEnabled: Boolean = this@TestClanManagementControllerModule.isEconomyEnabled
        override val economyProvider: EconomyProvider = this@TestClanManagementControllerModule.economyProvider
    }
    override val balanceValidation = BalanceValidation.BalanceValidation(balanceValidationModule)
}
