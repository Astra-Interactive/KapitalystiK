package ru.astrainteractive.kapitalystic.features.balancevalidation

import org.junit.Test
import ru.astrainteractive.astralibs.economy.EconomyProvider
import ru.astrainteractive.astralibs.economy.InMemoryEconomyProvider
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.balancevalidation.di.BalanceValidationModule
import java.util.UUID
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BalanceValidationComponentTest {
    @Test
    fun testEconomyEnabled() {
        val economyProvider = InMemoryEconomyProvider()
        val balanceValidationComponent = object : BalanceValidationModule {
            override val isEconomyEnabled: Boolean = true
            override val economyProvider: EconomyProvider = economyProvider
        }.let(::BalanceValidationComponent)
        val userDTO = UserDTO(UUID.randomUUID(), "user")
        economyProvider.addMoney(userDTO.minecraftUUID, 100.0)

        balanceValidationComponent.haveAtLeast(userDTO, 20).let(::assertTrue)
        balanceValidationComponent.haveAtLeast(userDTO, 100.0).let(::assertTrue)
        balanceValidationComponent.haveAtLeast(userDTO, 101.0).let(::assertFalse)

        assertFails {
            balanceValidationComponent.assertHaveAtLeast(userDTO, 102.0)
        }
    }

    @Test
    fun testEconomyDisabled() {
        val balanceValidationComponent = object : BalanceValidationModule {
            override val isEconomyEnabled: Boolean = false
            override val economyProvider: EconomyProvider = InMemoryEconomyProvider()
        }.let(::BalanceValidationComponent)
        val userDTO = UserDTO(UUID.randomUUID(), "user")

        balanceValidationComponent.haveAtLeast(userDTO, 20).let(::assertTrue)
        balanceValidationComponent.haveAtLeast(userDTO, 100.0).let(::assertTrue)
        balanceValidationComponent.haveAtLeast(userDTO, 101.0).let(::assertTrue)
        balanceValidationComponent.assertHaveAtLeast(userDTO, 102.0)
    }
}
