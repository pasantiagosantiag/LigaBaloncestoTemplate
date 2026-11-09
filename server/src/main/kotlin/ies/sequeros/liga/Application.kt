package ies.sequeros.liga

import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.*
import io.ktor.server.plugins.origin
import io.ktor.server.request.host
import io.ktor.server.request.port
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Serializable
data class FileItem(
    val name: String,
    val url: String
)
fun Application.module() {
    install(ContentNegotiation) {
json(Json {
    prettyPrint = true
    isLenient = true
} )
    }
    routing {
        staticResources("/static", "static")
       get("/"){
           call.respond("Práctica Liga Baloncesto")
       }
        get("images") {

                val scheme = call.request.origin.scheme
                val host = call.request.host()
                val port = call.request.port()

                // url base
                val baseUrl = "$scheme://$host:$port/static"


                val resource = javaClass.classLoader.getResource("static")
                val fileList = mutableListOf<FileItem>()

                if (resource != null) {
                    // Decodificar la ruta para gestionar correctamente espacios y caracteres especiales
                    val folderPath = URLDecoder.decode(resource.file, StandardCharsets.UTF_8)
                    val folder = File(folderPath)

                    if (folder.exists() && folder.isDirectory) {
                        folder.listFiles()
                            ?.filter { it.isFile }
                            ?.forEach { file ->
                                fileList.add(
                                    FileItem(
                                        name = file.name.substringBefore("."),
                                        url = "$baseUrl/${file.name}"
                                    )
                                )
                            }
                    }
                }


                call.respond(fileList)
            }
        }
    }
