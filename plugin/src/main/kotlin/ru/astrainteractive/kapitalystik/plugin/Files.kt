package ru.astrainteractive.kapitalystik.plugin

import ru.astrainteractive.astralibs.file_manager.SpigotFileManager

/**
 * All plugin files such as config.yml and other should only be stored here!
 */
object Files {
    val configFile: SpigotFileManager = SpigotFileManager("config.yml")
}
