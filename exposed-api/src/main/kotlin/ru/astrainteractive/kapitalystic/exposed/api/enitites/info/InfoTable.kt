package ru.astrainteractive.kapitalystic.exposed.api.enitites.info

import org.jetbrains.exposed.dao.id.LongIdTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable

internal object InfoTable : LongIdTable() {
    val orgID = reference("org_id", OrgTable)
    val message = text("message")
    val index = integer("index")
    val type = enumeration<InfoType>("info_type")
    init {
        uniqueIndex(orgID, index, type)
    }
}
