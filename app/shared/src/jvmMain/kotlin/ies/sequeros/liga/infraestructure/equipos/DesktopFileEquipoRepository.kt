package ies.sequeros.liga.infraestructure.equipos


import ies.sequeros.liga.domain.Equipo
import kotlinx.serialization.json.Json
import okio.FileSystem

class DesktopFileEquipoRepository(path: String) : InFileEquipoRepository(path) {


    private val fileSystem = FileSystem.SYSTEM


    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true // Opcional: da formato legible al JSON
    }



    override suspend fun saveToFile() {
        this.path.parent?.let { parentDir ->
            if (!fileSystem.exists(parentDir)) {
                fileSystem.createDirectories(parentDir)
            }
        }
        val jsonString = json.encodeToString(items.value.map {
            EquipoDTO.fromEquipo(it)
        })

        fileSystem.write(path) {
            writeUtf8(jsonString)
        }
    }

    override suspend fun loadFromFile(): List<Equipo> {
        if (!fileSystem.exists(path)) {
            return emptyList()
        }
        val jsonString = fileSystem.read(path) {
            readUtf8()
        }

        if (jsonString.isBlank()) {
            return emptyList()
        }
        return json.decodeFromString<List<EquipoDTO>>(jsonString).map {
            it.toEquipo()

        }
    }
}