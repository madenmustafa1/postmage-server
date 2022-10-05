package com.postmage.plugins

import com.postmage.controller.accessManager
import com.postmage.dependecy_injection.KoinApplication
import com.postmage.enums.userRouteRole
import com.postmage.util.HttpRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val koin = KoinApplication()
fun Application.configureRouting() {

    with(HttpRoute) {
        routing {
            get("/") { call.respondText("Hi!") }

            //Login
            route("") {
                get(SIGN_IN) { koin.loginVM.signIn(call) }
                post(SIGN_UP) { koin.loginVM.signUp(call) }
                put(CHANGE_PASSWORD) { koin.loginVM.changePassword(call) }
            }

            //Profile
            route (PROFILE) {
                get(MY_PROFILE) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        CoroutineScope(Dispatchers.Unconfined).launch {
                            koin.profileVM.getMyProfileInfo(call)
                        }
                    }
                }
                put(MY_PROFILE) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        CoroutineScope(Dispatchers.Unconfined).launch {
                            koin.profileVM.putMyProfileInfo(call)
                        }
                    }
                }

                get(MY_FOLLOWER) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        CoroutineScope(Dispatchers.Unconfined).launch {
                            koin.profileVM.getMyFollowerData(call)
                        }
                    }
                }
            }
        }
    }
}


