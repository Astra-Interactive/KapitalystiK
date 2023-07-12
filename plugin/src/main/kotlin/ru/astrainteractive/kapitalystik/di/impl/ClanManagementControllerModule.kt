package ru.astrainteractive.kapitalystik.di.impl

import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.kapitalystic.exposed.api.KapitalystiKDBApi
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation
import ru.astrainteractive.kapitalystic.features.balancevalidation.BalanceValidation.Companion.BalanceValidation
import ru.astrainteractive.kapitalystic.features.controllers.di.ClanManagementControllerModule
import ru.astrainteractive.kapitalystic.features.core.Configuration
import ru.astrainteractive.kapitalystic.features.core.Translation
import ru.astrainteractive.kapitalystic.features.failuremessenger.FailureMessenger
import ru.astrainteractive.kapitalystic.features.failuremessenger.FailureMessenger.Companion.FailureMessenger
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger
import ru.astrainteractive.kapitalystik.di.SpigotRootModule

class ClanManagementControllerModule : ClanManagementControllerModule {
    private val rootModule by SpigotRootModule
    override val platformMessenger: PlatformMessenger by rootModule.platformMessenger
    override val dbApi: KapitalystiKDBApi by rootModule.kapitalystiKApi
    override val economyProvider: EconomyProvider by rootModule.economyProvider
    override val configuration: Configuration by rootModule.configuration
    override val translation: Translation by rootModule.translation
    override val balanceValidation: BalanceValidation
        get() = BalanceValidation(BalanceValidationModuleModule())
    override val failureMessenger: FailureMessenger
        get() = FailureMessenger(FailureMessengerModule())
    override val messenger: PlatformMessenger by rootModule.platformMessenger
}
