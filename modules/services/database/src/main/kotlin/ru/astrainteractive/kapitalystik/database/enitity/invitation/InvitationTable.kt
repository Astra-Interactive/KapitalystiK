package ru.astrainteractive.kapitalystik.database.enitity.invitation

import org.jetbrains.exposed.dao.id.LongIdTable
import ru.astrainteractive.kapitalystik.database.enitity.org.OrgTable

object InvitationTable : LongIdTable() {
    val orgID = reference("org_id", OrgTable)
    val minecraftName = varchar("minecraft_name", 64)
    val minecraftUUID = varchar("minecraft_uuid", 64)

    init {
        uniqueIndex(orgID, minecraftName, minecraftUUID)
    }
}
