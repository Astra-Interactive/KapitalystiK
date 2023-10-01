package ru.astrainteractive.kapitalystik.database.enitity.org

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberDAO
import ru.astrainteractive.kapitalystik.database.enitity.member.MemberTable
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsDAO
import ru.astrainteractive.kapitalystik.database.enitity.warps.WarpsTable

class OrgDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<OrgDAO>(OrgTable)

    var tag by OrgTable.tag
    var name by OrgTable.name
    var status by OrgTable.status
    var description by OrgTable.description
    val members by MemberDAO referrersOn MemberTable.orgID
    val warps by WarpsDAO referrersOn WarpsTable.orgID
}
