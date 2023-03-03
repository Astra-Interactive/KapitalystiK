package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.kapitalystic.shared.core.SharedTranslation

/**
 * All translation stored here
 */
class Translation : BaseTranslation(), SharedTranslation {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: FileManager = FileManager("translations.yml")
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

    // General
    override val prefix = "#18dbd1[KapitalystiK]".HEX()
    override val reload = translationValue("general.reload", "#dbbb18Перезагрузка плагина")
    override val reloadComplete = translationValue("general.reload_complete", "#42f596Перезагрузка успешно завершена")
    override val noPermission = translationValue("general.no_permission", "#db2c18У вас нет прав!")
    override fun clanCreated(name: String, tag: String): String {
        TODO("Not yet implemented")
    }

    override fun notEnoughMoney(needAmount: Int): String {
        TODO("Not yet implemented")
    }

    override fun clanRenamed(newName: String): String {
        TODO("Not yet implemented")
    }
}
