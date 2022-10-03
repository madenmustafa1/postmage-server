package com.postmage.mongo_client

import com.mongodb.client.MongoDatabase
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.mongo_client.db_router.DBRouter
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class MongoInitialize {
    private val client = KMongo.createClient()
    private val database = client.getDatabase(DBRouter.DB_NAME)

    val getUserCollection = database.getCollection<UserProfileInfoModel>(DBRouter.USERS)

    fun getDB(): MongoDatabase = database

}