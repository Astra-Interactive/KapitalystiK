package ru.astrainteractive.kapitalystik.features.controller.di

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.economy.InMemoryEconomyProvider
import ru.astrainteractive.kapitalystik.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystik.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystik.api.di.factory.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystik.core.Configuration
import ru.astrainteractive.kapitalystik.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystik.features.balancevalidation.di.BalanceValidationContainer

class TestClanManagementControllerContainer(
    val isEconomyEnabled: Boolean = true,
    override val economyProvider: EconomyProvider = InMemoryEconomyProvider(),
    override val configuration: Configuration = Configuration()
) : ClanManagementControllerContainer {
    private val commonDBApi: KapitalystiKCommonDBApi = KapitalystiKCommonDBApiFactory().create()
    override val dbApi: KapitalystiKDBApi = KapitalystiKDBApiFactory(commonDBApi).create()
    private val balanceValidationContainer = object : BalanceValidationContainer {
        override val isEconomyEnabled: Boolean = this@TestClanManagementControllerContainer.isEconomyEnabled
        override val economyProvider: EconomyProvider = this@TestClanManagementControllerContainer.economyProvider
    }
    override val balanceValidation = BalanceValidation.BalanceValidation(balanceValidationContainer)
}
