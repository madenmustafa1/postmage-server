package com.postmage.model.image

import kotlinx.serialization.Serializable

@Serializable
data class DownloadPhotoRequestModel(
    val userId: String,
    val photoName: String
)