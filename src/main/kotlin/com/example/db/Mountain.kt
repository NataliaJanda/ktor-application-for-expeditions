package com.example.db

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
@Serializable
data class MountainInput (val nameMountain: String, val elevation: String)
@Serializable
data class Mountain(val idMountain: Int, val nameMountain: String, val elevation: String)

val mountainStorage = mutableListOf<Mountain>()

fun createMountain(nameMountain: String, elevation: String): Int {
    var generatedId = -1
    transaction {
        generatedId = Mountains.insert {
            it[Mountains.nameMountain] = nameMountain
            it[Mountains.elevation] = elevation
        } get Mountains.idMountain
    }
    return generatedId
}
