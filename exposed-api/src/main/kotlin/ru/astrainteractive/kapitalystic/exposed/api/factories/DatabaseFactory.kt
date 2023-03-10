package ru.astrainteractive.kapitalystic.exposed.api.factories

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation.InvitationTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.member.MemberTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.warps.WarpsTable
import java.sql.Connection

class DatabaseFactory(
    val path: String
) : Factory<Database>() {
    override fun initializer(): Database {
        return Database.connect("jdbc:sqlite:$path", "org.sqlite.JDBC").also {
            TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
            runBlocking {
                transaction(it) {
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
}
