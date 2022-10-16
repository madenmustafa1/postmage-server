package com.postmage.extensions

import com.postmage.util.Directory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun ByteArray?.writePhotoToDisk(photoName: String): Boolean {
    return try {
        if (this == null) return false

        val photoDir = File(Directory.userDesktopDir?.path, photoName)

        if (withContext(Dispatchers.IO) {
                photoDir.createNewFile()
            }) {
            File(Directory.userDesktopDir?.path, photoName).writeBytes(this)
        }
        true
    } catch (e: Exception) {
        false
    }
}