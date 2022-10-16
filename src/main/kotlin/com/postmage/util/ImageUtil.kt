package com.postmage.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class ImageUtil {
    private val photos: String = "photos"

    suspend fun imgPathToBase64(pathName: String, imgName: String): String{
        //val pathBytes = Loader.getResource("drawable/$photos/$pathName/$imgName.png")?.readBytes()
        //return pathBytes?.toBase64() ?: ""
        return ""
    }

    suspend fun writePhotoToDisk(photoName: String, photoBytes: ByteArray): Boolean {
        return try {
            val photoDir = File(Directory.userDesktopDir?.path, photoName)

            if (withContext(Dispatchers.IO) {
                    photoDir.createNewFile()
                }) {
                File(Directory.userDesktopDir?.path, photoName).writeBytes(photoBytes)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}