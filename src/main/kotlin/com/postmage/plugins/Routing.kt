package com.postmage.plugins

import com.postmage.*
import com.postmage.model.error.ErrorResponse
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

fun Application.configureRouting() {

    //val service = PersonService()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/person") {
            post {
                /*
                val request = call.receive<PersonDto>()
                val person = request.toPerson()
                service.create(person)
                    ?.let { userId ->
                        call.response.headers.append("My-User-Id-Header", userId.toString())
                        call.respond(HttpStatusCode.Created)
                    } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
                 */
            }
        }
    }
}


