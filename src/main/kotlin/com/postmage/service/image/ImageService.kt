package com.postmage.service.image

import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages

class ImageService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
): ImageInterface {
    override suspend fun downloadPhoto(userId: String, body: DownloadPhotoRequestModel): ResponseData<Boolean> {
        TODO("Not yet implemented")
    }
}