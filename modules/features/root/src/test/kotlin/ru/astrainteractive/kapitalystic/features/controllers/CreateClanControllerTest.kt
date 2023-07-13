package ru.astrainteractive.kapitalystic.features.controllers

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.DBException
import ru.astrainteractive.kapitalystic.exposed.api.factories.DatabaseFactory
import ru.astrainteractive.kapitalystic.features.controllers.di.TestClanManagementControllerModule
import ru.astrainteractive.kapitalystic.features.core.Configuration
import java.io.File
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class CreateClanControllerTest {
    @BeforeTest
    fun setup() {
        File("test").apply { if (exists()) delete() }
        DatabaseFactory("test").build()
    }

    @AfterTest
    fun destroy() {
        File("test").apply { if (exists()) delete() }
    }

    @Test
    fun test(): Unit = runBlocking {
        val createCost = 100
        val module = TestClanManagementControllerModule(
            configuration = Configuration(
                economy = Configuration.Economy(
                    create = createCost
                )
            )
        )
        val controller = CreateClanController(module)
        UserDTO(UUID.randomUUID(), "user").let { userDTO ->
            assertFailsWith<DBException.NotEnoughMoney> {
                controller.createClan(userDTO, "tag", "name")
            }
            module.economyProvider.addMoney(userDTO.minecraftUUID, createCost * 2.0)
            controller.createClan(userDTO, "tag", "name")
            assertFailsWith<DBException.AlreadyInOrganization> {
                controller.createClan(userDTO, "tag", "name")
            }
        }
        UserDTO(UUID.randomUUID(), "user").let { userDTO ->
            assertFailsWith<DBException.NotEnoughMoney> {
                controller.createClan(userDTO, "tag", "name")
            }
            module.economyProvider.addMoney(userDTO.minecraftUUID, createCost * 2.0)
            assertFailsWith<DBException.OrgAlreadyExists> {
                controller.createClan(userDTO, "tag", "name")
            }
        }
    }
}
