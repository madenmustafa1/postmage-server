package com.postmage.plugins

import com.postmage.controller.accessManager
import com.postmage.enums.userRouteRole
import com.postmage.koin
import com.postmage.util.HttpRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun Application.configureRouting() {

    with(HttpRoute) {
        routing {
            get("/") {
                call.respondText("Hello World!")
            }

            route("/person") {
                post {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        CoroutineScope(Dispatchers.Unconfined).launch {
                            println("asdasdsad")
                            //call.respondText("Hello World!2")
                            //call.response.status(HttpStatusCode.OK)
                        }
                    }
                }
            }


            route(SIGN_IN) {
                get {

                }
                post {
                    koin.loginVM.signUp(call)
                }
            }


            route("/sign-up") {
                get {

                }
                post { koin.loginVM.signUp(call) }
            }


        }
    }


}



// call.respondText("Hello World!2")


/*
val request = call.receive<PersonDto>()
val person = request.toPerson()
service.create(person)
    ?.let { userId ->
        call.response.headers.append("My-User-Id-Header", userId.toString())
        call.respond(HttpStatusCode.Created)
    } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)

                    authenticate {
                    get("/hello") {
                        //val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                        val principal = call.principal<JWTPrincipal>()
                        val username = principal!!.payload.getClaim("username").asString()
                        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
                    }
                }
 */


