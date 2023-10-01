package ru.astrainteractive.kapitalystik.database.enitity.member

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MemberDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<MemberDAO>(MemberTable)

    var minecraftName by MemberTable.minecraftName
    var minecraftUUID by MemberTable.minecraftUUID
    var orgID by MemberTable.orgID
    var isOwner by MemberTable.isOwner
}
