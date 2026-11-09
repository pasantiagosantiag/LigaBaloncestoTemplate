package ies.sequeros.liga.infraestructure.imagenes

import androidx.compose.runtime.State
import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.ImageRepository
import ies.sequeros.liga.domain.valueobjects.ImageURL
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestImageRepository: ImageRepository {
    protected val items: MutableStateFlow<List<ImageURL>> = MutableStateFlow(listOf())
    private val repositoryScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override  fun getAll(): Flow<List<ImageURL>> {
        if (items.value.isEmpty()) {
            //se ejecuta en una corrutina
            repositoryScope.launch {
                val client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json()
                    }
                }
                try {

                    val imageListDto: List<ImagenURLDto> = client.get("http://localhost:8080/images").body()

                    // Actualizamos el StateFlow. Esto notificará automáticamente a la UI
                    items.update { imageListDto.map { it.toDomain() } }

                } catch (e: Exception) {
                    println("Ocurrió un error en la petición: ${e.message}")
                } finally {
                    // Nos aseguramos de cerrar el cliente HTTP
                    client.close()
                }
            }
        }

        return items
    }

}