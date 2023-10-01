package ru.astrainteractive.kapitalystik.features.controller

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.astrainteractive.kapitalystik.api.exception.DBApiException
import ru.astrainteractive.kapitalystik.core.Configuration
import ru.astrainteractive.kapitalystik.database.di.factory.DatabaseFactory
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.controller.di.TestClanManagementControllerContainer
import java.io.File
import java.util.UUID
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

class CreateClanControllerTest {
    @BeforeTest
    fun setup(): Unit = runBlocking {
        File("test").apply { if (exists()) delete() }
        DatabaseFactory("test").create().also {
            DatabaseFactory.createSchema(it)
        }
    }

    @AfterTest
    fun destroy() {
        File("test").apply { if (exists()) delete() }
    }

    @Test
    fun test(): Unit = runBlocking {
        val createCost = 100
        val module = TestClanManagementControllerContainer(
            configuration = Configuration(
                economy = Configuration.Economy(
                    create = createCost
                )
            )
        )
        val controller = CreateClanController(module)
        UserDTO(UUID.randomUUID(), "user").let { userDTO ->
            assertFailsWith<DBApiException.NotEnoughMoney> {
                controller.createClan(userDTO, "tag", "name")
            }
            module.economyProvider.addMoney(userDTO.minecraftUUID, createCost * 2.0)
            controller.createClan(userDTO, "tag", "name")
            assertFailsWith<DBApiException.AlreadyInOrganization> {
                controller.createClan(userDTO, "tag", "name")
            }
        }
        UserDTO(UUID.randomUUID(), "user").let { userDTO ->
            assertFailsWith<DBApiException.NotEnoughMoney> {
                controller.createClan(userDTO, "tag", "name")
            }
            module.economyProvider.addMoney(userDTO.minecraftUUID, createCost * 2.0)
            assertFailsWith<DBApiException.OrgAlreadyExists> {
                controller.createClan(userDTO, "tag", "name")
            }
        }
    }
}
