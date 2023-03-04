package ru.astrainteractive.kapitalystic.exposed.api.enitites.warps

import org.jetbrains.exposed.dao.id.LongIdTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable

internal object WarpsTable : LongIdTable() {
    val orgID = reference("org_id", OrgTable)
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val worldName = varchar("world", 16)
    val type = enumeration<WarpType>("type")
    val isPrivate = bool("is_private")
}
