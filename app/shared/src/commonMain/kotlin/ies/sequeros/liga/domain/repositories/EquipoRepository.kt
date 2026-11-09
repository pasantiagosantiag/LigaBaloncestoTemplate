package ies.sequeros.liga.domain.repositories

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.valueobjects.EquipoId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EquipoRepository {
    suspend fun save(equipo: Equipo): Equipo
      fun getAll(): Flow<List<Equipo>>
    suspend fun getByEquipoId(equipoId: EquipoId): Equipo?

    suspend fun delete(equipoId: EquipoId)
    suspend fun deleteAll()
    suspend fun update(equipo: Equipo)

}