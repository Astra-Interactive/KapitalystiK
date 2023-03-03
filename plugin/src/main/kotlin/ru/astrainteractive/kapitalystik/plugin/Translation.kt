package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation

/**
 * All translation stored here
 */
class Translation : BaseTranslation(), SharedTranslation {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: FileManager = FileManager("translations.yml")

    val positiveColor = "#42f596"
    val negativeColor = "#db2c18"

    // General
    override val prefix = "#18dbd1[KapitalystiK]".HEX()
    override val reload = translationValue("general.reload", "${positiveColor}Перезагрузка плагина")
    override val reloadComplete = translationValue(
        "general.reload_complete",
        "${positiveColor}Перезагрузка успешно завершена"
    )
    override val noPermission = translationValue("general.no_permission", "${negativeColor}У вас нет прав!")

    override val alreadyInOrganization: String
        get() = TODO("Not yet implemented")
    override val alreadyInvited: String
        get() = TODO("Not yet implemented")
    override val notInvited: String
        get() = TODO("Not yet implemented")
    override val notOrganizationMember: String
        get() = TODO("Not yet implemented")
    override val notOrganizationOwner: String
        get() = TODO("Not yet implemented")
    override val unexcpectedException: String
        get() = TODO("Not yet implemented")

    override val disbanded: String
        get() = TODO("Not yet implemented")
    override val bioChanged: String
        get() = TODO("Not yet implemented")

    override fun clanCreated(name: String, tag: String): String {
        TODO("Not yet implemented")
    }

    override fun notEnoughMoney(needAmount: Int): String {
        TODO("Not yet implemented")
    }

    override fun clanRenamed(newName: String): String {
        TODO("Not yet implemented")
    }

    override fun userInvited(userDTO: UserDTO): String {
        TODO("Not yet implemented")
    }

    override fun userKicked(userDTO: UserDTO): String {
        TODO("Not yet implemented")
    }

    override fun ownershipTransferred(newOwner: UserDTO): String {
        TODO("Not yet implemented")
    }

    override fun joinedToClan(clanTAG: String): String {
        TODO("Not yet implemented")
    }

    override fun spawnPublic(value: Boolean): String {
        TODO("Not yet implemented")
    }
}
