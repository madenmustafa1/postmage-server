package com.postmage.service.image

import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.service.ResponseData

interface ImageInterface {

    suspend fun downloadPhoto(userId: String, body: DownloadPhotoRequestModel): ResponseData<Boolean>

}