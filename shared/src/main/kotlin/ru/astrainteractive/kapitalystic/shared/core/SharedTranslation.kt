package ru.astrainteractive.kapitalystic.shared.core

interface SharedTranslation {
    val alreadyInOrganization: String
    val alreadyInvited: String
    val notInvited: String
    val notOrganizationMember: String
    val notOrganizationOwner: String
    val unexcpectedException: String

    val prefix: String

    val reload: String

    val reloadComplete: String

    val noPermission: String
    fun clanCreated(name: String, tag: String): String
    fun notEnoughMoney(needAmount: Int): String
    fun clanRenamed(newName: String): String
}
