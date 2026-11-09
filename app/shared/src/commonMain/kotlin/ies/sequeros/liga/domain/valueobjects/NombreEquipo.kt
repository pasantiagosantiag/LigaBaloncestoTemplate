package ies.sequeros.liga.domain.valueobjects

import kotlinx.serialization.Serializable


data class NombreEquipo (val name: String) {
    init {
        require(name.isNotBlank()) { "El nombre puede estar vacío." }
        require(name.length<255) { "El nombre es demasiado largo." }

    }
}