package com.example.db

import kotlinx.serialization.Serializable

@Serializable
data class Person(val idPerson: Int, val namePerson: String, val phoneNumber: Long)

val personStorage = mutableListOf<Person>()


