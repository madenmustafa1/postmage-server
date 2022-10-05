package com.postmage.vm

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.model.profile.user.SetFollowersDataModel
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.plugins.koin
import com.postmage.repo.ProfileRepository
import com.postmage.util.AppMessages
import com.postmage.util.CoroutineCustomExceptionHandler
import com.postmage.util.sendException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileVM(
    private val repository: ProfileRepository,
    private val appMessages: AppMessages
) {

    fun getMyProfileInfo(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val result = repository.getMyProfileInfo(call.request.headers["Authorization"]!!)
                result.data?.let {
                    call.respond(it)
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }
                sendException(
                    call = call,
                    statusCode = result.message?.statusCode ?: 500,
                    errorMessage = result.message?.message ?: ""
                )
            } catch (e: NullPointerException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.UNAUTHORIZED,
                    errorMessage = koin.appMessages.UNAUTHORIZED
                )
            } catch (e: Exception) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.SERVER_ERROR,
                    errorMessage = koin.appMessages.SERVER_ERROR
                )
            }
        }
    }

    fun putMyProfileInfo(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = call.receive<UserProfileInfoModel>()
                val result = repository.putMyProfileInfo(call.request.headers["Authorization"]!!, body)

                result.data?.let {
                    call.respond(it)
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }
                sendException(
                    call = call,
                    statusCode = result.message?.statusCode ?: 500,
                    errorMessage = result.message?.message ?: ""
                )
            } catch (e: NullPointerException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.UNAUTHORIZED,
                    errorMessage = koin.appMessages.UNAUTHORIZED
                )
            } catch (e: MismatchedInputException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.BAD_REQUEST,
                    errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
                )
            } catch (e: Exception) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.SERVER_ERROR,
                    errorMessage = koin.appMessages.SERVER_ERROR
                )
            }
        }
    }

    fun getMyFollowerData(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val result = repository.getMyFollowerData(call.request.headers["Authorization"]!!)
                result.data?.let {
                    call.respond(it)
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }

                sendException(
                    call = call,
                    statusCode = result.message?.statusCode ?: 500,
                    errorMessage = result.message?.message ?: ""
                )
            } catch (e: NullPointerException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.UNAUTHORIZED,
                    errorMessage = koin.appMessages.UNAUTHORIZED
                )
            } catch (e: Exception) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.SERVER_ERROR,
                    errorMessage = koin.appMessages.SERVER_ERROR
                )
            }
        }
    }

    fun putMyFollowerData(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = call.receive<SetFollowersDataModel>()
                val result = repository.putMyFollowerData(call.request.headers["Authorization"]!!, body)

                result.data?.let {
                    call.respond(it)
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }
                sendException(
                    call = call,
                    statusCode = result.message?.statusCode ?: 500,
                    errorMessage = result.message?.message ?: ""
                )
            } catch (e: NullPointerException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.UNAUTHORIZED,
                    errorMessage = koin.appMessages.UNAUTHORIZED
                )
            } catch (e: MismatchedInputException) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.BAD_REQUEST,
                    errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
                )
            } catch (e: Exception) {
                sendException(
                    call = call,
                    statusCode = StatusCodeUtil.SERVER_ERROR,
                    errorMessage = koin.appMessages.SERVER_ERROR
                )
            }
        }
    }
}