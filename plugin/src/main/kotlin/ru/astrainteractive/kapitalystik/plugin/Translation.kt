package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation
import ru.astrainteractive.astralibs.utils.HEX

/**
 * All translation stored here
 */
class Translation : BaseTranslation() {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: FileManager = FileManager("translations.yml")

    val getByByCheck = translationValue("getByByCheck", "#db2c18getByByCheck")

    // Database
    val dbSuccess = translationValue("database.success", "#18dbd1Успешно подключено к базе данных")
    val dbFail = translationValue("database.fail", "#db2c18Нет подключения к базе данных")

    // General
    val prefix = "#18dbd1[KapitalystiK]".HEX()
    val reload = translationValue("general.reload", "#dbbb18Перезагрузка плагина")
    val reloadComplete = translationValue("general.reload_complete", "#42f596Перезагрузка успешно завершена")
    val noPermission = translationValue("general.no_permission", "#db2c18У вас нет прав!")
}
