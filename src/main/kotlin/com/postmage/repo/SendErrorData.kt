package repo

import enums.ErrorStatus
import service.ErrorMessage
import service.ResponseData

fun sendErrorData(message: String, statusCode: Int? = null) =
    ResponseData.error(
        ErrorMessage(
            message = message,
            statusCode = statusCode ?: ErrorStatus.SERVER_ERROR
        ), null
    )
