package com.postmage.mongo_client

import com.postmage.mongo_client.db_router.DBRouter
import org.litote.kmongo.KMongo

class MongoInitialize {
    private val client = KMongo.createClient()
    private val database = client.getDatabase(DBRouter.DB_NAME)

    fun getDB() = database

}