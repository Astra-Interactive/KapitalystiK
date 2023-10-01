package ru.astrainteractive.kapitalystik.exposed.api.enitites.org

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.astrainteractive.kapitalystik.exposed.api.enitites.member.MemberDAO
import ru.astrainteractive.kapitalystik.exposed.api.enitites.member.MemberTable
import ru.astrainteractive.kapitalystik.exposed.api.enitites.warps.WarpsDAO
import ru.astrainteractive.kapitalystik.exposed.api.enitites.warps.WarpsTable

internal class OrgDAO(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<OrgDAO>(OrgTable)

    var tag by OrgTable.tag
    var name by OrgTable.name
    var status by OrgTable.status
    var description by OrgTable.description
    val members by MemberDAO referrersOn MemberTable.orgID
    val warps by WarpsDAO referrersOn WarpsTable.orgID
}
