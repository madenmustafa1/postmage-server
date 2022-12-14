package com.postmage.plugins

import com.postmage.controller.accessManager
import com.postmage.dependecy_injection.KoinApplication
import com.postmage.enums.PostType
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
                post(SIGN_IN) { koin.loginVM.signIn(call) }
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

                put(PROFILE_PHOTO) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.profileVM.putMyProfilePhoto(call)
                    }
                }
            }

            //Posts
            route(POSTS) {
                get(POST) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getPost(call)
                    }
                }

                get(COMMENTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getComments(call)
                    }
                }

                post(ADD_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.addPost(call)
                    }
                }

                post(ADD_POSTS_TO_GROUP) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.addPost(call, PostType.ADD_GROUP)
                    }
                }

                get(MY_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.getMyPost(call)
                    }
                }

                put(UPDATE_POSTS) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.usersPostsVM.updatePost(call)
                    }
                }

                post(GROUP_POSTS) {
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

                get(MY_GROUP_LIST) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.getMyGroupList(call)
                    }
                }

                get(MY_GROUP_INFO) {
                    accessManager(call, role = userRouteRole().toTypedArray()) {
                        koin.groupVM.getMyGroupInfo(call)
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


