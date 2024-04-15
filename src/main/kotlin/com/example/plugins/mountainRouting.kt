package com.example.plugins
import com.example.db.Mountain
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.db.mountainStorage
import io.ktor.server.request.*

fun Route.mountainRouting() {
    route("/mountain") {
        get{
            if (mountainStorage.isNotEmpty()) {
                call.respond(mountainStorage)
            } else {
                call.respondText("No mountain found", status = HttpStatusCode.OK)
            }

        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val mountain =
                mountainStorage.find { it.idMountain == id.toInt() } ?: return@get call.respondText(
                    "No mountain with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(mountain)
        }
        post {
            val mountain = call.receive<Mountain>()
            mountainStorage.add(mountain)
            call.respondText("Mountain stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {}
    }
}