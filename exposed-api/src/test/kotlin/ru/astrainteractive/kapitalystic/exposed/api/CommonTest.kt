package ru.astrainteractive.kapitalystic.exposed.api

import kotlinx.coroutines.runBlocking
import ru.astrainteractive.kapitalystic.api.KapitalystiKCommonDBApi
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class CommonTest : ORMTest() {
    val commonApi = KapitalystiKCommonDBApiFactory().value
    val api = KapitalystiKDBApiFactory(commonApi).value
    val randomUserDTO: UserDTO
        get() = UserDTO(
            minecraftUUID = UUID.randomUUID(),
            minecraftName = UUID.randomUUID().toString()
        )

    @Test
    fun testCreation(): Unit = runBlocking {
        val creator = randomUserDTO
        val orgDTO = api.create("tag", "name", creator).getOrThrow()
        assertEquals(orgDTO.name, "name")
        assertEquals(orgDTO.tag, "tag")
    }
}