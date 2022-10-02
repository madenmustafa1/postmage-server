package com.postmage.vm

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import enums.ErrorStatus
import io.javalin.http.Context
import koin
import kotlinx.coroutines.*
import model.login.password.ChangePasswordModel
import model.login.sign_in.SignInRequestModel
import model.login.sign_up.SignUpRequestModel
import com.postmage.repo.LoginRepository
import repo.sendErrorData
import service.Status
import util.CoroutineCustomExceptionHandler
import java.lang.Exception

class LoginVM(
    private val repository: LoginRepository
) {
    /*
    fun signIn(context: Context) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = context.bodyAsClass(SignInRequestModel::class.java)
                val result = repository.singIn(body)
                result.data?.let {
                    context.json(it)
                    context.status(200)
                    return@launch
                }
                context.json(result.message ?: "")
                context.status(result.message?.statusCode ?: 500)
            } catch (e: MismatchedInputException) {
                context.json(sendErrorData(koin.appMessages.MODEL_IS_NOT_VALID, ErrorStatus.BAD_REQUEST))
                context.status(ErrorStatus.BAD_REQUEST)
            } catch (e: Exception) {
                context.json(sendErrorData(koin.appMessages.SERVER_ERROR))
                context.status(500)
            }
        }
    }

    fun signUp(context: Context) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = context.bodyAsClass(SignUpRequestModel::class.java)
                val result = repository.singUp(body)
                (result.message ?: result.data)?.let { context.json(it) }
                if (result.status == Status.SUCCESS) {
                    context.status(200)
                    return@launch
                }
                context.status(result.message?.statusCode ?: 500)
            } catch (e: MismatchedInputException) {
                context.json(sendErrorData(koin.appMessages.MODEL_IS_NOT_VALID, ErrorStatus.BAD_REQUEST))
                context.status(ErrorStatus.BAD_REQUEST)
            } catch (e: Exception) {
                context.json(sendErrorData(koin.appMessages.SERVER_ERROR))
                context.status(500)
            }
        }
    }

    fun changePassword(context: Context) {
        CoroutineScope(Dispatchers.Unconfined).launch {
            repository.changePassword(ChangePasswordModel("", "", "", ""))
        }
    }
     */
}