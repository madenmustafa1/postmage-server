package com.postmage.repo

import com.postmage.model.image.DownloadPhotoRequestModel

import com.postmage.service.ResponseData
import com.postmage.service.image.ImageInterface
import com.postmage.service.image.ImageService
import com.postmage.util.AppMessages

class ImageRepository(
    private val imageService: ImageService,
    private val appMessages: AppMessages
): ImageInterface {
    override suspend fun downloadPhoto(userId: String, body: DownloadPhotoRequestModel): ResponseData<Boolean> {
        return imageService.downloadPhoto(userId, body)
    }
}