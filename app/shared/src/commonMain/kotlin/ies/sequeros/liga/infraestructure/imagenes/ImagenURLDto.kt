package ies.sequeros.liga.infraestructure.imagenes

import ies.sequeros.liga.domain.valueobjects.ImageURL
import kotlinx.serialization.Serializable

@Serializable
data class ImagenURLDto(
    val name: String,
    val url: String
){
    fun toDomain(): ImageURL = ImageURL(

        name = name,
        url = url
    )
}