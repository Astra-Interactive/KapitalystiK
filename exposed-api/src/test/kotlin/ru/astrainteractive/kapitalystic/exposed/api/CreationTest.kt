package ru.astrainteractive.kapitalystic.exposed.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.utils.ORMTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * This class will test creation of organizations
 */
class CreationTest : ORMTest() {


    @Test
    fun `Create single organization`(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        assertEquals(orgDTO.name, "name")
        assertEquals(orgDTO.tag, "tag")
    }
    @Test
    fun `Create multiple organization from single user`(): Unit = runBlocking {
        val creator = randomUserDTO
        api.create("tag", "name", creator).getOrThrow()
        assertThrows<Throwable> {
            api.create("tag", "name", creator).getOrThrow()
        }
    }
    @Test
    fun `Create multiple organization with same tag from different users`(): Unit = runBlocking {
        api.create("tag", "name", randomUserDTO).getOrThrow()
        assertThrows<Throwable> {
            api.create("tag", "name", randomUserDTO).getOrThrow()
        }
        assertDoesNotThrow {
            runBlocking {
                api.create("tag2", "name2", randomUserDTO).getOrThrow()
            }
        }
    }
}