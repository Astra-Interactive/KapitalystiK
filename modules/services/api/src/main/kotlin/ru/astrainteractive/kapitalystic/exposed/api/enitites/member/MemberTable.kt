package ru.astrainteractive.kapitalystic.exposed.api.enitites.member

import org.jetbrains.exposed.dao.id.LongIdTable
import ru.astrainteractive.kapitalystic.exposed.api.enitites.org.OrgTable

internal object MemberTable : LongIdTable() {
    val orgID = reference("org_id", OrgTable)
    val minecraftName = varchar("minecraft_name", 64).uniqueIndex()
    val minecraftUUID = varchar("minecraft_uuid", 64).uniqueIndex()
    val isOwner = bool("is_owner")
}
