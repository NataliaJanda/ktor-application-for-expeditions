package com.example.db

import kotlinx.serialization.Serializable

@Serializable
data class Mountain(val idMountain: Int, val nameMountain: String, val elevation: String)

val mountainStorage = mutableListOf<Mountain>()
