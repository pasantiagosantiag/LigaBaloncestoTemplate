plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
     id("org.jetbrains.kotlin.plugin.serialization") version ("2.4.21")
}

group = "ies.sequeros.liga"
version = "1.0.0"
application {
    mainClass = "ies.sequeros.liga.ApplicationKt"
}

dependencies {
    api(project(":core"))
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3") // O la versión acorde a tu Kotlin
    implementation("io.ktor:ktor-server-content-negotiation:3.6.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.6.0")
}