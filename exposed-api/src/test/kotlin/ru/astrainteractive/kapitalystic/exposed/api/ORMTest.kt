package ru.astrainteractive.kapitalystic.exposed.api

import org.jetbrains.exposed.sql.Database
import kotlinx.coroutines.runBlocking
import ru.astrainteractive.astralibs.orm.DBConnection
import ru.astrainteractive.astralibs.orm.exception.DatabaseException
import java.io.File
import kotlin.test.*


abstract class ORMTest {
    protected var database: Database? = null
    fun assertConnected(): Database {
        return database ?: throw DatabaseException.DatabaseNotConnectedException
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