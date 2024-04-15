package com.example.plugins
import com.example.db.Person
import com.example.db.personStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun  Route.personRouting() {
    route("/person") {
        get { if (personStorage.isNotEmpty()) {
            call.respond(personStorage)
        } else {
            call.respondText("No person found", status = HttpStatusCode.OK)
        }

        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val person =
                personStorage.find {it.idPerson == id.toInt() } ?: return@get call.respondText(
                    "No person with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(person)
        }
        post {
            val person = call.receive<Person>()
            personStorage.add(person)
            call.respondText("Person stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
        }
    }
}