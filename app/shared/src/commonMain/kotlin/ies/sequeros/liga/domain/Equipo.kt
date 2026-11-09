package ies.sequeros.liga.domain

import ies.sequeros.liga.domain.valueobjects.EquipoId
import ies.sequeros.liga.domain.valueobjects.ImageURL
import ies.sequeros.liga.domain.valueobjects.NombreEquipo
import kotlinx.serialization.Serializable


data class Equipo(
    val equipoId: EquipoId= EquipoId.random(),
    var nombreEquipo: NombreEquipo,
    var imagenEquipo: ImageURL,

)