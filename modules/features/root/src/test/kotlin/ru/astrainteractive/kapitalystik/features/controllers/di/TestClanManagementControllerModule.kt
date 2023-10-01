package ru.astrainteractive.kapitalystik.features.controllers.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.economy.InMemoryEconomyProvider
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystik.exposed.api.factories.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystik.features.balancevalidation.di.BalanceValidationModule
import ru.astrainteractive.kapitalystik.features.core.Configuration

class TestClanManagementControllerModule(
    val isEconomyEnabled: Boolean = true,
    override val economyProvider: EconomyProvider = InMemoryEconomyProvider(),
    override val configuration: Configuration = Configuration()
) : ClanManagementControllerModule {
    private val commonDBApi: KapitalystiKCommonDBApi = KapitalystiKCommonDBApiFactory().create()
    override val dbApi: KapitalystiKDBApi = KapitalystiKDBApiFactory(commonDBApi).create()
    private val balanceValidationModule = object : BalanceValidationModule {
        override val isEconomyEnabled: Boolean = this@TestClanManagementControllerModule.isEconomyEnabled
        override val economyProvider: EconomyProvider = this@TestClanManagementControllerModule.economyProvider
    }
    override val balanceValidation = BalanceValidation.BalanceValidation(balanceValidationModule)
}
