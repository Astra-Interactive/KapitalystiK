package ru.astrainteractive.kapitalystik.utils

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.shared.utils.MessageHandler

class SpigotMessageHandler : MessageHandler {
    override fun sendMessage(userDTO: UserDTO, message: String) {
        Bukkit.getPlayer(userDTO.minecraftUUID)?.sendMessage(message.HEX())
    }
}
