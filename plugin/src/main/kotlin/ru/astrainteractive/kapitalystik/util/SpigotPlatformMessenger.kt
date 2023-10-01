package ru.astrainteractive.kapitalystik.util

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.util.hex
import ru.astrainteractive.kapitalystik.dto.UserDTO
import ru.astrainteractive.kapitalystik.features.platformmessenger.PlatformMessenger

class SpigotPlatformMessenger : PlatformMessenger {
    override fun sendMessage(userDTO: UserDTO, message: String) {
        Bukkit.getPlayer(userDTO.minecraftUUID)?.sendMessage(message.hex())
    }
}
