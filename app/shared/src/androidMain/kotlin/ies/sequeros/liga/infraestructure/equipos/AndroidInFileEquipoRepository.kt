package ies.sequeros.liga.infraestructure.equipos

import android.content.Context
import ies.sequeros.liga.domain.Equipo
import kotlinx.serialization.json.Json


class AndroidInFileEquipoRepository(private val contexto: Context, path:String) : InFileEquipoRepository(path){

    override suspend fun saveToFile() {
        val equiposDTO=items.value.map { EquipoDTO.fromEquipo(it) }
         val jsonString = Json.encodeToString(equiposDTO)
     contexto.openFileOutput(this.path.toString(), Context.MODE_PRIVATE).use { outputStream ->
         outputStream.write(jsonString.toByteArray())
     }
    }

    override suspend fun loadFromFile(): List<Equipo> {
        val jsonString = contexto.openFileInput(this.path.toString()).bufferedReader().use { it.readText() }
        val dtos = Json.decodeFromString<List< EquipoDTO>>(jsonString)
        return dtos.map { it.toEquipo() }
    }
}