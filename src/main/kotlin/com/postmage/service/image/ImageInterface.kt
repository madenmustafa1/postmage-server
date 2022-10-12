package com.postmage.service.image

import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.service.ResponseData
import java.io.File

interface ImageInterface {

    suspend fun downloadPhoto(userId: String, body: DownloadPhotoRequestModel): ResponseData<File>

}