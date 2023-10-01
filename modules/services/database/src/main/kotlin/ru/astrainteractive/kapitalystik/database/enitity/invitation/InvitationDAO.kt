package ru.astrainteractive.kapitalystik.database.enitity.invitation

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class InvitationDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<InvitationDAO>(InvitationTable)

    var minecraftName by InvitationTable.minecraftName
    var minecraftUUID by InvitationTable.minecraftUUID
    var orgID by InvitationTable.orgID
}
