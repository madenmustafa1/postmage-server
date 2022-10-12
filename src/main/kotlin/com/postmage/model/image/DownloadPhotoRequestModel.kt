package com.postmage.model.image

import kotlinx.serialization.Serializable

@Serializable
data class DownloadPhotoRequestModel(
    val objectId: String,
    val photoName: String
)