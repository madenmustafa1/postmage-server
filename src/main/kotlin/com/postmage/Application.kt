package com.postmage

import com.postmage.dependecy_injection.KoinApplication
import com.postmage.dependecy_injection.injectModule
import io.ktor.server.application.*
import com.postmage.plugins.*
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)



@Suppress("unused")
fun Application.module() {
    injectKoin()
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}

val koin = KoinApplication()


fun injectKoin() {
    startKoin {
        printLogger()
        modules(injectModule())
    }
}



