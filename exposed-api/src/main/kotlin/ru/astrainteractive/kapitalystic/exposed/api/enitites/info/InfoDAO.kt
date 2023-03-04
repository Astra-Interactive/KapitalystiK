package ru.astrainteractive.kapitalystic.exposed.api.enitites.info

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

internal class InfoDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<InfoDAO>(InfoTable)

    var orgID by InfoTable.orgID
    var message by InfoTable.message
    var index by InfoTable.index
    var type by InfoTable.type
}
