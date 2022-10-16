package com.postmage.mongo_client

import com.mongodb.client.MongoDatabase
import com.postmage.model.group.GroupInfoModel
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.model.sign_up.SignUpRequestModel
import com.postmage.mongo_client.db_router.DBRouter
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class MongoInitialize {
    private val client = KMongo.createClient()
    private val database = client.getDatabase(DBRouter.DB_NAME)

    val getUserCollection = database.getCollection<UserProfileInfoModel>(DBRouter.USERS)
    val signUpUserCollection = database.getCollection<SignUpRequestModel>(DBRouter.USERS)
    val getUsersPostsCollection = database.getCollection<GetUserPostModel>(DBRouter.USERS_POSTS)
    val getGroupsCollection = database.getCollection<GroupInfoModel>(DBRouter.GROUPS)

    fun getDB(): MongoDatabase = database

}