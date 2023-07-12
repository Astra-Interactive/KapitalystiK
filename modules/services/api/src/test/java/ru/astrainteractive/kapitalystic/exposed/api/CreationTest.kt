package ru.astrainteractive.kapitalystic.exposed.api

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.kapitalystic.exposed.api.utils.ORMTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

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
        assertFailsWith<Throwable> {
            api.create("tag", "name", creator).getOrThrow()
        }
    }

    @Test
    fun `Create multiple organization with same tag from different users`(): Unit = runBlocking {
        api.create("tag", "name", randomUserDTO).getOrThrow()
        assertFailsWith<Throwable> {
            api.create("tag", "name", randomUserDTO).getOrThrow()
        }
        runBlocking {
            api.create("tag2", "name2", randomUserDTO).getOrThrow()
        }
    }
}
