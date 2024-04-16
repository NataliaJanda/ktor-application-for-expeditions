package com.example.db

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class ExpeditionInput(val nameExpedition: String, val mountainName: Int)
@Serializable
data class Expedition(val idExpedition: Int, val nameExpedition: String, val mountainName: Int)

val expeditionStorage = mutableListOf<Expedition>()
fun createExpedition(nameExpedition: String, mountainName: Int): Int {
    var generatedId = -1
    transaction {
        generatedId = Expeditions.insert {
            it[Expeditions.nameExpedition] = nameExpedition
            it[Expeditions.mountainName] = mountainName
        } get Expeditions.idExpedition
    }
    return generatedId
}
