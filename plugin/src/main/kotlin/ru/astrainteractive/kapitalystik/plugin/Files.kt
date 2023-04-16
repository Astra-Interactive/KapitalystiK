package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.file_manager.SpigotFileManager
import ru.astrainteractive.astralibs.filemanager.DefaultFileManager
import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.kapitalystik.KapitalystiK

/**
 * All plugin files such as config.yml and other should only be stored here!
 */
object Files {
    val configFile: FileManager = DefaultFileManager(KapitalystiK::class.java,"config.yml",KapitalystiK.instance.dataFolder)
}
