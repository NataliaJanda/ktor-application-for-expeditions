package com.example.plugins
import com.example.db.Expedition
import com.example.db.expeditionStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.expeditionRouting() {
    route("/expedition") {
        get{
            if (expeditionStorage.isNotEmpty()) {
            call.respond(expeditionStorage)
        } else {
            call.respondText("No expedition found", status = HttpStatusCode.OK)
        }

        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val expedition =
                expeditionStorage.find {it.idExpedition == id.toInt() } ?: return@get call.respondText(
                    "No expedition with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(expedition)
        }
        post {
            val expedition = call.receive<Expedition>()
            expeditionStorage.add(expedition)
            call.respondText("Expedition stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {

        }
    }
}