package com.postmage.service.user_posts

import com.mongodb.BasicDBObject
import com.mongodb.client.model.Filters.all
import com.mongodb.client.model.Filters.`in`
import com.postmage.enums.PostType
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.group.GroupIdModel
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.followed_users.PostOfFollowedUsers
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.model.posts.update_posts.UpdateUserPostModel
import com.postmage.model.profile.user.SingleFollowerDataModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.mongo_client.mongo_constants.MongoSort
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import org.bson.types.ObjectId
import org.litote.kmongo.find

class UserPostsService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : UserPostsInterface {
    override suspend fun addPost(
        userId: String,
        body: AddPostModel,
        addPostType: PostType
    ): ResponseData<Boolean> {
        //Find user following data
        val userIdQuery = BasicDBObject("userId", userId)
        var nameSurname = ""

        mongoDB.getUserCollection.find(userIdQuery)
            .forEach { nameSurname = it.nameSurname ?: "" }

        val model = GetUserPostModel(
            groupId = body.groupId,
            photoName = body.photoName,
            description = body.description,
            objectId = ObjectId.get().toString(),
            creationTime = body.creationTime,
            userId = userId,
            nameSurname = nameSurname
        )
        var isSuccess = false

        if (body.groupId.trim().isNotEmpty()) {
            //Get <Group> collection
            val groupQuery = BasicDBObject("groupId", body.groupId)
            mongoDB.getGroupsCollection.find(groupQuery).limit(1).forEach { groupModel ->
                //Users Control
                for (i in groupModel.groupUsers) {
                    if (i.id == userId) {
                        mongoDB.getUsersPostsCollection.insertOne(model)
                        isSuccess = true
                        break
                    }
                }
            }
        } else mongoDB.getUsersPostsCollection.insertOne(model).also { isSuccess = true }

        return ResponseData.success(isSuccess)
    }

    override suspend fun getMyPost(userId: String): ResponseData<List<GetUserPostModel>> {
        val query = BasicDBObject("userId", userId)
        val sortDescQuery = BasicDBObject("creationTime", MongoSort.DESC)
        val userPostList = arrayListOf<GetUserPostModel>()

        mongoDB
            .getUsersPostsCollection
            .find(query)
            .sort(sortDescQuery)
            .forEach { userPostList.add(it) }

        return ResponseData.success(userPostList)
    }

    override suspend fun getGroupPost(userId: String, body: GroupIdModel): ResponseData<List<GetUserPostModel>> {
        val query = BasicDBObject("groupId", body.groupId!!)
        var isSuccess = false

        //Get <Group> collection
        mongoDB.getGroupsCollection.find(query).limit(1).forEach { groupModel ->
            //Users Control
            for (i in groupModel.groupUsers) {
                if (i.id == userId) {
                    isSuccess = true
                    break
                }
            }
        }

        if (!isSuccess) return sendErrorData(
            appMessages.ACCESS_DENIED,
            StatusCodeUtil.FORBIDDEN
        )

        //Get <UsersPosts> collection
        val sortDescQuery = BasicDBObject("creationTime", MongoSort.DESC)
        val postList = arrayListOf<GetUserPostModel>()
        mongoDB
            .getUsersPostsCollection
            .find(query)
            .sort(sortDescQuery)
            .forEach { postList.add(it) }

        return ResponseData.success(postList)
    }

    override suspend fun updatePost(userId: String, body: UpdateUserPostModel): ResponseData<GetUserPostModel> {
        var model: GetUserPostModel? = null

        //Get <UsersPosts> collection
        val query = BasicDBObject("objectId", body.objectId!!)
        mongoDB
            .getUsersPostsCollection
            .find(query)
            .limit(1).forEach {
                if (body.description != null) it.description = body.description!!
                if (body.comment != null) {
                    if (it.comment == null) it.comment = arrayListOf(body.comment!!)
                    else it.comment!!.add(body.comment!!)
                }
                if (body.likeUserId != null) {
                    if (it.likeUserId == null) it.likeUserId = arrayListOf(body.likeUserId!!)
                    else {
                        val result = it.likeUserId!!.removeIf { id ->
                            id == body.likeUserId
                        }
                        if (!result) it.likeUserId!!.add(body.likeUserId!!)
                    }
                }

                mongoDB.getUsersPostsCollection.replaceOne(query, it)
                model = it
            }

        model?.let {
            return ResponseData.success(model)
        } ?: run {
            return sendErrorData(
                appMessages.POST_NOT_FOUND,
                statusCode = StatusCodeUtil.BAD_REQUEST,
            )
        }
    }

    override suspend fun postOfFollowedUsers(
        userId: String,
        body: PostOfFollowedUsers
    ): ResponseData<List<GetUserPostModel>> {
        //Find user following data
        val userIdQuery = BasicDBObject("userId", userId)
        val followingData: ArrayList<SingleFollowerDataModel> = arrayListOf()

        mongoDB.getUserCollection.find(userIdQuery)
            .forEach { followingData.addAll(it.following) }

        val userIdList: ArrayList<String> = arrayListOf()
        userIdList.add(userId)
        followingData.forEach { userIdList.add(it.userId) }

        //Example Value -> { userId: { $in: ["635d730e82882a05b4598829", "633ddd71dde4000752533909"] } }

        //Get posts
        val sortDescQuery = BasicDBObject("creationTime", MongoSort.DESC)
        val userPostList = arrayListOf<GetUserPostModel>()
        mongoDB.getUsersPostsCollection
            .find(`in`("userId", userIdList))
            .sort(sortDescQuery)
            .forEach { userPostList.add(it) }

        return ResponseData.success(userPostList)
    }


}