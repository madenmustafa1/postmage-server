package com.postmage

import io.ktor.server.application.*
import com.postmage.plugins.*
import org.litote.kmongo.Id
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}



