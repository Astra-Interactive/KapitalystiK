package ru.astrainteractive.kapitalystic.shared.utils

import ru.astrainteractive.kapitalystic.dto.UserDTO

interface MessageHandler {
    fun sendMessage(userDTO: UserDTO, message: String)
}
