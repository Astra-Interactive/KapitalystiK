package ru.astrainteractive.kapitalystic.exposed.api.enitites.org

import org.jetbrains.exposed.dao.id.LongIdTable

internal object OrgTable : LongIdTable() {
    val tag = varchar("tag", 32).uniqueIndex()
    val name = varchar("name", 32).uniqueIndex()
    val status = text("status")
    val description = text("description")
}