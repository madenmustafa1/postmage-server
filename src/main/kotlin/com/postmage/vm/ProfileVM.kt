package com.postmage.vm

import com.postmage.repo.ProfileRepository

class ProfileVM(private val repository: ProfileRepository) {
/*
    fun getMyProfileInfo(context: Context) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val result = repository.getMyProfileInfo(context.header("Authorization")!!)

                result.data?.let {
                    context.json(it)
                    context.status(200)
                    return@launch
                }
                context.json(result.message ?: "")
                context.status(result.message?.statusCode ?: 500)
            } catch (e: Exception) {
                context.json(sendErrorData(koin.appMessages.SERVER_ERROR))
                context.status(ErrorStatus.SERVER_ERROR)
            }
        }
    }

    fun putMyProfileInfo(context: Context) {
        CoroutineScope(Dispatchers.Unconfined + CoroutineCustomExceptionHandler.handler).launch {
            try {
                val body = context.bodyAsClass(UserProfileInfoModel::class.java)
                val result = repository.putMyProfileInfo(context.header("Authorization")!!, body)

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
                println(e.message)
                context.json(sendErrorData(koin.appMessages.SERVER_ERROR))
                context.status(ErrorStatus.SERVER_ERROR)
            }
        }
    }

 */
}