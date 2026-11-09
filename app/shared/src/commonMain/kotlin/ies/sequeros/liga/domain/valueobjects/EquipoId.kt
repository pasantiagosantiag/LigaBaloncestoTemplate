package ies.sequeros.liga.domain.valueobjects

import ies.sequeros.liga.domain.Equipo
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

 class EquipoId private constructor (val value: Uuid) {
     val id: Uuid
         get() = value
    companion object {
        fun from(id: Uuid): EquipoId {
            return EquipoId(id)
        }
        fun random(): EquipoId {
            return EquipoId(Uuid.random())
        }
    }
}