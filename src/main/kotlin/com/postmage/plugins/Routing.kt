package com.postmage.plugins

import com.postmage.controller.accessManager
import com.postmage.dependecy_injection.KoinApplication
import com.postmage.enums.userRouteRole
import com.postmage.extensions.makeFolder
import com.postmage.util.Directory
import com.postmage.util.HttpRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val koin = KoinApplication()

fun Application.configureRouting() {
    Directory.usersFile.makeFolder("users")

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
                        koin.profileVM.getMyProfileInfo(call)
                    }
                }
                put(MY_PROFILE) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.profileVM.putMyProfileInfo(call)
                    }
                }

                get(MY_FOLLOWER) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.profileVM.getMyFollowerData(call)
                    }
                }

                put(MY_FOLLOWER) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        CoroutineScope(Dispatchers.Unconfined).launch {
                            koin.profileVM.putMyFollowerData(call)
                        }
                    }
                }


            }

            //Posts
            route(POSTS) {
                post(ADD_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.addPost(call)
                    }
                }

                get(MY_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getMyPost(call)
                    }
                }
            }

            //Image
            route(IMAGE) {
                get(DOWNLOAD) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.imageVM.downloadPhoto(call)
                    }
                }
            }

            //Home
            route(HOME) {
                get(USERS_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {

                    }
                }
            }
        }
    }
}


