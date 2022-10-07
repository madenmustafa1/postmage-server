package com.postmage.vm

import com.postmage.repo.ProfileRepository
import com.postmage.util.AppMessages
import com.postmage.util.Directory
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class UserPostsVM(
    private val repository: ProfileRepository,
    private val appMessages: AppMessages
) {

    suspend fun addPost(call: ApplicationCall) {
        var fileDescription = ""
        var fileName = ""

        val multipartData = call.receiveMultipart()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    fileDescription = part.value
                }

                is PartData.FileItem -> {
                    fileName = part.originalFileName as String
                    val photoDir = File(Directory.userDesktopDir?.path, fileName)

                    if (withContext(Dispatchers.IO) {
                            photoDir.createNewFile()
                        }) {
                        println("File created: " + photoDir.name)

                        val fileBytes = part.streamProvider().readBytes()
                        File(Directory.userDesktopDir?.path, fileName).writeBytes(fileBytes)
                    }
                }
                else -> {}
            }
        }
        call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")

    }
}