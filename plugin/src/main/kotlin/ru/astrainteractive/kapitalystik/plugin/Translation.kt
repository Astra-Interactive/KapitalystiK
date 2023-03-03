package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.kapitalystic.shared.SharedTranslation

/**
 * All translation stored here
 */
class Translation : BaseTranslation(), SharedTranslation {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: FileManager = FileManager("translations.yml")

    // General
    override val prefix = "#18dbd1[KapitalystiK]".HEX()
    override val reload = translationValue("general.reload", "#dbbb18Перезагрузка плагина")
    override val reloadComplete = translationValue("general.reload_complete", "#42f596Перезагрузка успешно завершена")
    override val noPermission = translationValue("general.no_permission", "#db2c18У вас нет прав!")
}
