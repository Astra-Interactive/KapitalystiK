package ru.astrainteractive.kapitalystic.features.core

import ru.astrainteractive.kapitalystic.dto.UserDTO

/**
 * Shared translation interface to use it on both Spigot and other platforms
 */
interface Translation {

    // General
    val wrongUsage: String

    val prefix: String

    val reload: String

    val reloadComplete: String

    val noPermission: String

    val disbanded: String

    val bioChanged: String

    val ruleAdded: String

    val spawnSet: String

    // DBError

    val alreadyInOrganization: String

    val alreadyInvited: String

    val notInvited: String

    val notPlayer: String

    val notOrganizationMember: String

    val notOrganizationOwner: String

    val unexcpectedException: String

    fun clanCreated(name: String, tag: String): String
    fun notEnoughMoney(needAmount: Int): String
    fun clanRenamed(newName: String): String
    fun userInvited(userDTO: UserDTO): String
    fun userKicked(userDTO: UserDTO): String
    fun ownershipTransferred(newOwner: UserDTO): String
    fun joinedToClan(clanTAG: String): String
    fun spawnPublic(value: Boolean): String
}
