package ru.astrainteractive.kapitalystic.exposed.api.utils

import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.kapitalystic.exposed.api.factories.DatabaseFactory
import ru.astrainteractive.klibs.kdi.Factory

object MockDatabaseFactory : Factory<Database> {

    override fun create(): Database {
        return DatabaseFactory("test_db.db").create()
    }
}