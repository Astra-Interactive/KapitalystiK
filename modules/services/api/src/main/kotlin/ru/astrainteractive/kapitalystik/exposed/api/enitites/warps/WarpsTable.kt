package ru.astrainteractive.kapitalystik.exposed.api.enitites.warps

import org.jetbrains.exposed.dao.id.LongIdTable
import ru.astrainteractive.kapitalystik.exposed.api.enitites.org.OrgTable

internal object WarpsTable : LongIdTable() {
    val orgID = reference("org_id", OrgTable)
    val tag = varchar("tag", 16).uniqueIndex()
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val worldName = varchar("world", 16)
    val isPrivate = bool("is_private")
}
