package com.postmage.plugins

import com.postmage.util.http_log.HttpLog
import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.application.*

fun Application.configureMonitoring() {
    val httpLog =  HttpLog()
    install(CallLogging) {
        level = Level.INFO
        filter { call -> httpLog.filterHttpLog(call) }
    }
}