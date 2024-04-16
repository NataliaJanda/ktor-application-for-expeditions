package com.example.plugins
import com.example.db.*
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
            val inputExpedition = call.receive<ExpeditionInput>()
            val id = createExpedition(inputExpedition.nameExpedition, inputExpedition.mountainName)
            val expedition = Expedition(id, inputExpedition.nameExpedition, inputExpedition.mountainName)
            expeditionStorage.add(expedition)
            call.respondText("Expedition stored correctly with id $id", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (expeditionStorage.removeIf { it.idExpedition == id.toInt() }) {
                call.respondText("Expedition removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}