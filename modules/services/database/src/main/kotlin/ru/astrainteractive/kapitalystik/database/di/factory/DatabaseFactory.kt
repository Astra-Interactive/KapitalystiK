package ru.astrainteractive.kapitalystik.database.di.factory

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.kapitalystik.database.enitity.invitation.InvitationTable
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberTable
import ru.astrainteractive.kapitalystik.database.enitity.org.OrgTable
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsTable
import ru.astrainteractive.klibs.kdi.Factory
import java.sql.Connection

class DatabaseFactory(
    val path: String?
) : Factory<Database> {
    override fun create(): Database {
        val database = path?.let {
            Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC")
        } ?: Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        return database
    }

    companion object {
        suspend fun createSchema(database: Database) {
            TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
            transaction(database) {
                addLogger(StdOutSqlLogger)
                val entities = buildList {
                    add(InvitationTable)
                    add(MemberTable)
                    add(OrgTable)
                    add(WarpsTable)
                }
                entities.forEach(SchemaUtils::create)
                entities.forEach(SchemaUtils::addMissingColumnsStatements)
            }
        }
    }
}
