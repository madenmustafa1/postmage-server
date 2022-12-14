package com.postmage.util

object HttpRoute {
    //Login
    const val SIGN_IN: String = "sign-in"
    const val SIGN_UP: String = "sign-up"
    const val CHANGE_PASSWORD: String = "change-password"

    //App
    const val HOME: String = "/home"

    //PROFILE
    const val PROFILE = "/profile"
    const val MY_PROFILE: String = "/my-profile"
    const val FOLLOWER: String = "/follower-data"
    const val PROFILE_PHOTO: String = "/profile-photo"


    //Posts
    const val POSTS = "/user-posts"
    const val POST = "/get-post"
    const val COMMENTS = "/get-comments"
    const val MY_POSTS = "/my-posts"
    const val GROUP_POSTS = "/group-posts"
    const val ADD_POSTS = "/add-posts"
    const val UPDATE_POSTS = "/update-posts"
    const val ADD_POSTS_TO_GROUP = "/add-posts-group"

    const val USERS_POSTS: String = "/followed-users-posts"


    //Group
    const val GROUP = "/group"
    const val CREATE_GROUP = "/create-group"
    const val ADD_USERS_GROUP = "/add-users-to-group"
    const val ADD_ADMIN_GROUP = "/add-admin-to-group"
    const val REMOVE_USERS_GROUP = "/remove-users-to-group"
    const val MY_GROUP_LIST = "/my-group-list"
    const val MY_GROUP_INFO = "/my-group-info"

    //Image
    const val IMAGE = "/image"
    const val DOWNLOAD = "/download"

}