package com.postmage.vm

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.password.ChangePasswordModel
import com.postmage.model.sign_in.SignInRequestModel
import com.postmage.model.sign_up.SignUpRequestModel
import com.postmage.plugins.koin
import com.postmage.repo.LoginRepository
import com.postmage.repo.sendErrorData
import com.postmage.service.ErrorMessage
import com.postmage.service.Status
import com.postmage.util.CoroutineCustomExceptionHandler
import com.postmage.util.GsonUtil
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginVM(
    private val repository: LoginRepository
) {

    fun signIn(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = call.receive<SignInRequestModel>()
                val result = repository.singIn(body)
                result.data?.let {
                    call.respond(it)
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }

                call.response.status(StatusCodeUtil.errHandle(result.message?.statusCode ?: 500))
                call.respond(result.message ?: "")
            } catch (e: MismatchedInputException) {
                call.response.status(StatusCodeUtil.errHandle(StatusCodeUtil.BAD_REQUEST))
                call.respond(
                    GsonUtil.gsonToJson(
                        sendErrorData<ErrorMessage>(koin.appMessages.MODEL_IS_NOT_VALID, StatusCodeUtil.BAD_REQUEST)
                    )
                )
            } catch (e: Exception) {
                call.response.status(StatusCodeUtil.errHandle(StatusCodeUtil.SERVER_ERROR))
                call.respond(
                    GsonUtil.gsonToJson(
                        sendErrorData<ErrorMessage>(koin.appMessages.SERVER_ERROR)
                    )
                )
            }
        }
    }

    fun signUp(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = call.receive<SignUpRequestModel>()
                val result = repository.singUp(body)
                (result.message ?: result.data)?.let { call.respond(it) }
                if (result.status == Status.SUCCESS) {
                    call.response.status(HttpStatusCode.OK)
                    return@launch
                }
                call.response.status(StatusCodeUtil.errHandle(result.message?.statusCode ?: 500))
            } catch (e: MismatchedInputException) {
                call.response.status(StatusCodeUtil.errHandle(StatusCodeUtil.BAD_REQUEST))
                call.respond(
                    GsonUtil.gsonToJson(
                        sendErrorData<ErrorMessage>(koin.appMessages.MODEL_IS_NOT_VALID, StatusCodeUtil.BAD_REQUEST)
                    )
                )
            } catch (e: Exception) {
                call.response.status(StatusCodeUtil.errHandle(StatusCodeUtil.SERVER_ERROR))
                call.respond(
                    GsonUtil.gsonToJson(
                        sendErrorData<ErrorMessage>(koin.appMessages.SERVER_ERROR)
                    )
                )
            }
        }
    }

    fun changePassword(call: ApplicationCall) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.changePassword(ChangePasswordModel("", "", "", ""))
        }
    }


}