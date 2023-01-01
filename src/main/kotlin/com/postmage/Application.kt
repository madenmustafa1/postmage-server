package com.postmage

import com.postmage.dependecy_injection.injectModule
import io.ktor.server.application.*
import com.postmage.plugins.*
import org.koin.core.context.startKoin
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    injectKoin()
    //configureSockets()
    configureSerialization()
    configureMonitoring()
    //configureSecurity()
    configureRouting()
}

fun injectKoin() {
    startKoin {
        printLogger()
        modules(injectModule())
    }
}


/*
 Sample SSL Code With Ktor


var keyStorePassword = "secretPassword"
var keyStoreAlias = "postMageAlias"

 private val KeyStore.asFile: File
    get() {
        val keyStoreFile = File("build/keystore.jks")
        this.saveToFile(keyStoreFile, keyStorePassword)
        return keyStoreFile
    }

     val keyStore = buildKeyStore {
        certificate(keyStoreAlias) {
            hash = HashAlgorithm.SHA384
            sign = SignatureAlgorithm.RSA
            keySizeInBits = 4096
            daysValid = 3
            password = keyStorePassword
        }
    }

    //keyStore.saveToFile(keyStoreFile, "secretPassword")
    keyStore.saveToFile(keyStore.asFile, keyStorePassword)


    val server = embeddedServer(Netty, applicationEngineEnvironment {
        sslConnector(keyStore,
            keyStoreAlias,
            { keyStorePassword.toCharArray() },
            { keyStorePassword.toCharArray() }) {

            keyStorePath = keyStore.asFile.absoluteFile

            module {
                // Your usual definitions, such as Content Negotiation, go here
                routing {
                    get("/") { call.respondText ("Hi!") }
                }
            }
        }

        developmentMode = true
    })
    server.start()
 */