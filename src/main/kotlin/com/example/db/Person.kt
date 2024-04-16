package com.example.db

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class PersonInput(val namePerson: String, val phoneNumber: Long, val expeditionID: Int)
@Serializable
data class Person(val idPerson: Int, val namePerson: String, val phoneNumber: Long, val expeditionID: Int)

val personStorage = mutableListOf<Person>()

fun createPerson(namePerson: String, phoneNumber: Long, expeditionID: Int): Int {
    var generatedId = -1
    transaction {
        generatedId = People.insert {
            it[People.namePerson] = namePerson
            it[People.phoneNumber] = phoneNumber
            it[People.expeditionID] = expeditionID
        } get People.idPerson
    }
    return generatedId
}