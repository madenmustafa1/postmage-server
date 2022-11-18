package com.postmage.service.image

import com.mongodb.BasicDBObject
import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import com.postmage.util.Directory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class ImageService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
): ImageInterface {
    override suspend fun downloadPhoto(userId: String, body: DownloadPhotoRequestModel): ResponseData<File> {
        val photoDir = File(Directory.userDesktopDir?.path, body.photoName)

        if (withContext(Dispatchers.IO) { photoDir.isFile }) {
            if (body.objectId != null) {
                val query = BasicDBObject("objectId", body.objectId)
                repeat(mongoDB.getUsersPostsCollection.find(query).limit(1).count()) {
                    return ResponseData.success(photoDir)
                }
            }
            else return ResponseData.success(photoDir)

        } else return sendErrorData(appMessages.PHOTO_NOT_FOUND)

        return sendErrorData(appMessages.ACCESS_DENIED)
    }
}