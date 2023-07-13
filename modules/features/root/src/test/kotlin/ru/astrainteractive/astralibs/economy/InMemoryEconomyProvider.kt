package ru.astrainteractive.astralibs.economy

import java.util.UUID

class InMemoryEconomyProvider : EconomyProvider {
    private val moneyMap = mutableMapOf<UUID, Double>()
    override fun addMoney(uuid: UUID, amount: Double): Boolean {
        moneyMap[uuid] = getBalance(uuid) + amount
        return true
    }

    override fun getBalance(uuid: UUID): Double {
        return moneyMap[uuid] ?: 0.0
    }

    override fun hasAtLeast(uuid: UUID, amount: Double): Boolean {
        return getBalance(uuid) >= amount
    }

    override fun takeMoney(uuid: UUID, amount: Double): Boolean {
        if (!hasAtLeast(uuid, amount)) return false
        moneyMap[uuid] = getBalance(uuid) - amount
        return true
    }
}
