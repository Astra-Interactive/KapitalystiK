package ru.astrainteractive.kapitalystik.util

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.utils.hex
import ru.astrainteractive.kapitalystic.dto.UserDTO
import ru.astrainteractive.kapitalystic.features.platformmessenger.PlatformMessenger

class SpigotPlatformMessenger : PlatformMessenger {
    override fun sendMessage(userDTO: UserDTO, message: String) {
        Bukkit.getPlayer(userDTO.minecraftUUID)?.sendMessage(message.hex())
    }
}
