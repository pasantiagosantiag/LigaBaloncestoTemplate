package ies.sequeros.liga.infraestructure.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.valueobjects.EquipoId
import ies.sequeros.liga.domain.valueobjects.ImageURL
import ies.sequeros.liga.domain.valueobjects.NombreEquipo
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class ImageURLDto(val nombre: String, val url: String){
    fun toImageURL() = ImageURL(url,nombre)
    companion object  {
             fun fromImageURL(imageURL: ImageURL): ImageURLDto {
            return ImageURLDto(imageURL.name, imageURL.url)
        }
    }
}
@Serializable
data class EquipoDTO(val id: Uuid,
    val name:String,
    val imagenURLDto:ImageURLDto) {
    fun toEquipo(): Equipo {
        return Equipo(
            EquipoId.from(id),
            NombreEquipo(name),
            imagenURLDto.toImageURL()

        )
    }
    companion object  {
        fun fromEquipo(equipo: Equipo): EquipoDTO {
            return EquipoDTO(equipo.equipoId.id,equipo.nombreEquipo.name,
                ImageURLDto.fromImageURL(equipo.imagenEquipo)
            )
        }
    }
}