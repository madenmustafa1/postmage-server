val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    id("io.ktor.plugin") version "2.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
}

group = "com.postmage"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    //mainClass.set("com.postmage.ApplicationKt")


    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    //SSL
    //implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    //testImplementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    //Auth
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    //Logger
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")


    val kmongo_version: String by project

    // Inside the dependencies
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

    //val koin_ktor: String by project
    val koin_ktor: String = "3.2.2"
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    // SLF4J Logger
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor")
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koin_ktor")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koin_ktor")

    val koin_version = "3.2.2"
    // Koin Core features
    implementation("io.insert-koin:koin-core:$koin_version")
    // Koin Test features
    testImplementation("io.insert-koin:koin-test:$koin_version")
    implementation("io.insert-koin:koin-ksp-compiler-jvm:1.0.3")

    //Gson & Json
    implementation("com.google.code.gson:gson:2.10")
    //Bcrypt
    implementation("at.favre.lib:bcrypt:0.9.0")

}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }

    docker {
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        localImageName.set("postmage")
        imageTag.set("0.0.1")

        portMappings.set(listOf(
            io.ktor.plugin.features.DockerPortMapping(
                80,
                8080,
                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
            )
        ))

        externalRegistry.set(
            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
                appName = provider { "postmage" },
                username = providers.environmentVariable("secret"),
                password = providers.environmentVariable("secret")
            )
        )
    }
}