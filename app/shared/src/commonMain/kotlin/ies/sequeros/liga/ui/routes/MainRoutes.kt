package ies.sequeros.liga.ui.routes

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid
@Serializable
sealed interface MainRoutes: NavKey {

    @Serializable
    object ListadoEquipos : MainRoutes
    @Serializable
    class DetalleEquipo(val id: Uuid?=null) : MainRoutes
}
