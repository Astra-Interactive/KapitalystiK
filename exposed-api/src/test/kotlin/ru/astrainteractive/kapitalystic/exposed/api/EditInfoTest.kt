package ru.astrainteractive.kapitalystic.exposed.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertThrows
import ru.astrainteractive.kapitalystic.api.DBException
import ru.astrainteractive.kapitalystic.exposed.api.utils.ORMTest
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class EditInfoTest : ORMTest() {

    @Test
    fun `Rename organization`(): Unit = runBlocking {
        val owner = randomUserDTO
        api.create("tag", "name", owner).getOrThrow()
        api.rename("new_name", owner).getOrThrow()
        commonApi.fetchOrg("tag").also { orgDTO ->
            assertEquals("new_name", orgDTO.name)
        }
    }
    @Test
    fun `Set organization description`(): Unit = runBlocking {
        val owner = randomUserDTO
        api.create("tag", "name", owner).getOrThrow()
        api.setDescription("new description", owner).getOrThrow()
        commonApi.fetchOrg("tag").also { orgDTO ->
            assertEquals("new description", orgDTO.description)
        }
    }
    @Test
    fun `Set organization bio`(): Unit = runBlocking {
        val owner = randomUserDTO
        api.create("tag", "name", owner).getOrThrow()
        api.setStatus("new bio", owner).getOrThrow()
        commonApi.fetchOrg("tag").also { orgDTO ->
            assertEquals("new bio", orgDTO.status)
        }
    }

    @Test
    fun `Change org info as not an owner`(): Unit = runBlocking {

        api.create("tag", "name", randomUserDTO).getOrThrow()

        assertThrows<DBException> {
            api.setStatus("new bio", randomUserDTO).getOrThrow()
        }
        assertThrows<DBException> {
            api.setDescription("new bio", randomUserDTO).getOrThrow()
        }
        assertThrows<DBException> {
            api.rename("new bio", randomUserDTO).getOrThrow()
        }
    }
}