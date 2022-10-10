package com.postmage.controller

import com.postmage.enums.AppUserRole
import com.postmage.extensions.verifyToken
import com.postmage.plugins.koin
import com.postmage.repo.sendErrorData
import com.postmage.service.ErrorMessage
import com.postmage.util.GsonUtil
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


suspend fun accessManager(call: ApplicationCall, vararg role: AppUserRole, accessRoute: suspend() -> Unit) {
    val header = call.request.headers["Authorization"] ?: return sendAuthStatus(call)
    val res = header.verifyToken(userRole = role)
    if (!res) return sendAuthStatus(call)
    accessRoute()
}


private fun sendAuthStatus(call: ApplicationCall) {
    CoroutineScope(Dispatchers.Unconfined).launch {
        call.response.status(HttpStatusCode.Unauthorized)
        call.respond(
            GsonUtil.gsonToJson(
                sendErrorData<ErrorMessage>(koin.appMessages.UNAUTHORIZED),
            ),
        )
    }
}