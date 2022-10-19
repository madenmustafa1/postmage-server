package com.postmage.plugins

import com.postmage.controller.accessManager
import com.postmage.dependecy_injection.KoinApplication
import com.postmage.enums.userRouteRole
import com.postmage.extensions.makeFolder
import com.postmage.util.Directory
import com.postmage.util.HttpRoute
import com.postmage.vm.GroupVM
import com.postmage.vm.UserPostsVM
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
            route(PROFILE) {
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

                get(FOLLOWER) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.profileVM.getMyFollowerData(call)
                    }
                }

                put(FOLLOWER) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.profileVM.putMyFollowerData(call)
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

                post(ADD_POSTS_TO_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.addPost(call, UserPostsVM.AddPostType.ADD_GROUP)
                    }
                }

                get(MY_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getMyPost(call)
                    }
                }

                get(GROUP_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getGroupPost(call)
                    }
                }

                get(USERS_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.postOfFollowedUsers(call)
                    }
                }
            }

            //Group
            route(GROUP) {
                post(CREATE_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.createGroup(call)
                    }
                }

                put(ADD_USERS_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.usersToGroup(call, GroupVM.UsersToGroupRequestType.ADD_USER)
                    }
                }

                put(ADD_ADMIN_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.usersToGroup(call, GroupVM.UsersToGroupRequestType.ADD_ADMIN)
                    }
                }

                put(REMOVE_USERS_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.usersToGroup(call, GroupVM.UsersToGroupRequestType.REMOVE)
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

            }
        }
    }
}


