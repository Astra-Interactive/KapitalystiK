package ru.astrainteractive.kapitalystic.exposed.api.utils

import org.jetbrains.exposed.sql.Database
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.orm.exception.DatabaseException
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKCommonDBApiFactory
import ru.astrainteractive.kapitalystic.exposed.api.factories.KapitalystiKDBApiFactory
import java.io.File
import java.util.*
import kotlin.test.*


abstract class ORMTest {
    val randomUserDTO: UserDTO
        get() = UserDTO(
            minecraftUUID = UUID.randomUUID(),
            minecraftName = UUID.randomUUID().toString()
        )
    protected var database: Database? = null
    fun assertConnected(): Database {
        return database ?: throw DatabaseException.DatabaseNotConnectedException
    }

    val commonApi by lazy {
        KapitalystiKCommonDBApiFactory().value
    }
    val api by lazy {
        KapitalystiKDBApiFactory(commonApi).value
    }

    @BeforeTest
    open fun setup(): Unit = runBlocking {
        database = MockDatabaseFactory.value
    }

    @AfterTest
    open fun destroy(): Unit = runBlocking {
        database?.connector?.invoke()?.close()
        database = null
        File("test_db.db").delete()
    }
}