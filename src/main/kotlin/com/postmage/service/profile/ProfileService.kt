package service.profile

import com.google.gson.Gson
import com.mongodb.BasicDBObject
import com.mongodb.client.MongoCollection
import model.profile.get_my_profile.GetMyProfileInfoRequestModel
import model.profile.user.FollowersDataModel
import model.profile.user.UserProfileInfoModel
import mongo_client.MongoInitialize
import mongo_client.db_router.DBRouter
import org.bson.Document
import repo.sendErrorData
import com.postmage.service.ResponseData
import util.AppMessages

class ProfileService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : ProfileInterface {
    override suspend fun getMyProfileInfo(userId: String): ResponseData<UserProfileInfoModel?> {
        val collection: MongoCollection<Document> = mongoDB.getDB()!!.getCollection(DBRouter.USERS)
        val query = BasicDBObject("userId", userId)

        collection.find(query).limit(1).forEach {
            val gson = Gson()
            val model = gson.fromJson(it.toJson(), UserProfileInfoModel::class.java)
            return ResponseData.success(model)
        }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean> {
        val collection: MongoCollection<Document> = mongoDB.getDB()!!.getCollection(DBRouter.USERS)
        val query = BasicDBObject("userId", userId)

        collection.find(query).limit(1).forEach {
            body.group?.let { group -> it["group"] = group }
            body.gender?.let { gender -> it["gender"] = gender }
            body.phoneNumber?.let { phoneNumber -> it["phoneNumber"] = phoneNumber }
            body.nameSurname?.let { nameSurname -> it["nameSurname"] = nameSurname }
            body.followingSize?.let { followingSize -> it["followingSize"] = followingSize }
            body.followersSize?.let { followersSize -> it["followersSize"] = followersSize }
            body.profilePhotoUrl?.let { profilePhotoUrl -> it["profilePhotoUrl"] = profilePhotoUrl }
            collection.replaceOne(query, it);
            return ResponseData.success(true)
        }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun getMyFollowerData(
        userId: String,
        body: GetMyProfileInfoRequestModel
    ): ResponseData<FollowersDataModel?> {
        TODO("Not yet implemented")
    }

    override suspend fun putMyFollowerData(userId: String, body: FollowersDataModel): ResponseData<Boolean> {
        TODO("Not yet implemented")
    }
}