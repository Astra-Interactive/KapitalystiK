package ru.astrainteractive.kapitalystik.dto

import java.util.UUID

class MemberDTO(
    val id: Long,
    val orgID: Long,
    val minecraftName: String,
    val minecraftUUID: UUID,
    val isOwner: Boolean
)
