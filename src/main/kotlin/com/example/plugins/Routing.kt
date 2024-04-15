package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/health-check") {
            call.response.status(HttpStatusCode.OK)
        }
        expeditionRouting()
        personRouting()
        mountainRouting()
    }

}
