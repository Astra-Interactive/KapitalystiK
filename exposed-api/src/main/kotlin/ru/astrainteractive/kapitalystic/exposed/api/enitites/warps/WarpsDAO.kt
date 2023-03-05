package ru.astrainteractive.kapitalystic.exposed.api.enitites.warps

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class WarpsDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WarpsDAO>(WarpsTable)

    var orgID by WarpsTable.orgID
    var tag by WarpsTable.tag
    var x by WarpsTable.x
    var y by WarpsTable.y
    var z by WarpsTable.z
    var worldName by WarpsTable.worldName
    var isPrivate by WarpsTable.isPrivate
}
