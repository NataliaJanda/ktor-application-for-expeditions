package com.example.plugins
import com.example.db.*
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
            val inputPerson = call.receive<PersonInput>()
            val id = createPerson(inputPerson.namePerson, inputPerson.phoneNumber, inputPerson.expeditionID)
            val person = Person(id, inputPerson.namePerson, inputPerson.phoneNumber, inputPerson.expeditionID)
            personStorage.add(person)
            call.respondText("Person stored correctly with id $id", status = HttpStatusCode.Created)
        }

        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (personStorage.removeIf { it.idPerson == id.toInt() }) {
                call.respondText("Person removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}