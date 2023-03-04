package ru.astrainteractive.kapitalystic.exposed.api.enitites.invitation

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class InvitationDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<InvitationDAO>(InvitationTable)

    var minecraftName by InvitationTable.minecraftName
    var minecraftUUID by InvitationTable.minecraftUUID
    var orgID by InvitationTable.orgID
}
