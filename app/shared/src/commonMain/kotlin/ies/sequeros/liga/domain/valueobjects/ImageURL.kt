package ies.sequeros.liga.domain.valueobjects

import kotlinx.serialization.Serializable


data class ImageURL (val url: String, val name: String) {
    init {
        require(name.isNotBlank()) { "El nombre de la imagen no puede estar vacío." }
        require(url.isNotBlank()) { "La URL no puede estar vacía." }
        require(url.startsWith("http://") || url.startsWith("https://")) {
            "La URL debe ser válida y comenzar con http:// o https://"
        }
    }
}