package com.postmage.vm

import com.postmage.enums.StatusCodeUtil
import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.plugins.koin
import com.postmage.repo.ImageRepository
import com.postmage.util.AppMessages
import com.postmage.util.sendException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ImageVM(
    private val repository: ImageRepository,
    private val appMessages: AppMessages
) {

    suspend fun downloadPhoto(call: ApplicationCall) {
        try {
            val body = DownloadPhotoRequestModel(
                photoName = call.request.queryParameters["photoName"]!!,
                objectId = call.request.queryParameters["objectId"],
            )

            val result = repository.downloadPhoto(call.request.headers["Authorization"]!!, body)
            result.data?.let {
                call.respondFile(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: appMessages.SERVER_ERROR
            )
        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: CannotTransformContentToTypeException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.BAD_REQUEST,
                errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
            )
        } catch (e: Exception) {
            println(e.message)
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = koin.appMessages.SERVER_ERROR
            )
        }
    }
}