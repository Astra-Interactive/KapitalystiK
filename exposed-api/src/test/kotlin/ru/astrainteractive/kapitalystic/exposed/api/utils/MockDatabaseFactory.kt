package ru.astrainteractive.kapitalystic.exposed.api.utils

import org.jetbrains.exposed.sql.Database
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.kapitalystic.exposed.api.factories.DatabaseFactory

object MockDatabaseFactory : Factory<Database>() {
    override fun initializer(): Database {
        return DatabaseFactory("test_db.db").value
    }
}