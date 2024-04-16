package com.example.plugins
import com.example.db.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.select


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
            val inputMountain = call.receive<MountainInput>()
            val id = createMountain(inputMountain.nameMountain, inputMountain.elevation)
            val mountain = Mountain(id, inputMountain.nameMountain, inputMountain.elevation)
            mountainStorage.add(mountain)
            call.respondText("Mountain stored correctly with id $id", status = HttpStatusCode.Created)
        }
            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val mountainId = id.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid mountain ID")

                val mountain = transaction { Mountains.selectAll().where { Mountains.idMountain eq mountainId }.singleOrNull() }

                if (mountain != null) {
                    transaction { Mountains.deleteWhere { Mountains.idMountain eq mountainId } }
                    call.respondText("Mountain removed correctly", status = HttpStatusCode.Accepted)
                } else {
                    call.respondText("Mountain not found", status = HttpStatusCode.NotFound)
                }
            }



    }
}