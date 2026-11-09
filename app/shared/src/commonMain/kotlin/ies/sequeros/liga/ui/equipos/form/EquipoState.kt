package ies.sequeros.liga.ui.equipos.form

import ies.sequeros.liga.domain.Equipo

data class EquipoFormState(
    val equipoId: String? = null,
    val nombreEquipo: String = "",
    val imagenNombre: String = "",
    val imagenUrl: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    // Validación en tiempo real de los campos
    val isValid: Boolean
        get() {
            val isNombreValid = nombreEquipo.isNotBlank()
            val isImagenNombreValid = imagenNombre.isNotBlank()
            val isUrlValid = isValidUrl(imagenUrl)

            return isNombreValid && isImagenNombreValid && isUrlValid
        }
    private fun isValidUrl(url: String): Boolean {
        if (url.isBlank()) return false

        return url.startsWith("http://") || url.startsWith("https://")
    }



    companion object {
        fun fromDomain(equipo: Equipo): EquipoFormState {
            return EquipoFormState(
                equipoId = equipo.equipoId.value.toString(),
                nombreEquipo = equipo.nombreEquipo.name,
                imagenNombre = equipo.imagenEquipo.name,
                imagenUrl = equipo.imagenEquipo.url
            )
        }
    }
}