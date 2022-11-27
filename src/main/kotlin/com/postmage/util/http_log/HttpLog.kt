package com.postmage.util.http_log

import io.ktor.server.application.*
import io.ktor.server.request.*

class HttpLog {

    fun filterHttpLog(call: ApplicationCall): Boolean  {
        return call.request.path().startsWith("/")
    }

    // TODO: İstekler db'ye kaydedilecek.
    fun saveRequest(request: ApplicationRequest) {
    }

}