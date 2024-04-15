package com.example.db

import kotlinx.serialization.Serializable

@Serializable
data class Expedition(val idExpedition: Int, val nameExpedition: String, val mountain: Int)

val expeditionStorage = mutableListOf<Expedition>()

