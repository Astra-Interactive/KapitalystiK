package ru.astrainteractive.kapitalystik.features.platformmessenger

import ru.astrainteractive.kapitalystik.dto.UserDTO

/**
 * This class allows to send player a message without usign platform-specific code(spigot/Fabric etc)
 */
interface PlatformMessenger {
    /**
     * Sends a message to player
     * @param userDTO - player to which message will be sent
     * @param message - a message!!!!!!
     */
    fun sendMessage(userDTO: UserDTO, message: String)
}
