package ies.sequeros.liga.infraestructure.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.domain.valueobjects.EquipoId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

open class InMemoryEquipoRepository(): EquipoRepository {

    protected val items: MutableStateFlow<List<Equipo>> = MutableStateFlow(listOf())
    override  fun getAll(): Flow<List<Equipo>> {

        return items.asFlow()
    }

    override suspend fun getByEquipoId(equipoId: EquipoId): Equipo? {
        return items.value.firstOrNull { it.equipoId == equipoId }
    }



    override suspend fun save(equipo: Equipo): Equipo {
        items.update {
            it.map {
                if (equipo.equipoId.id == it.equipoId.id) {
                    equipo
                } else
                    it
            }
        }

        return equipo
    }
    fun saveee():Unit{

    }
    override suspend fun delete(equipoId: EquipoId) {
        items.update {
            it.filter {
                equipoId.id!=it.equipoId.id
            }
        }


    }

    override suspend fun deleteAll() {
        items.update { listOf() }
    }

    override suspend fun update(equipo: Equipo) {
        items.update {
            it.map {
                if(equipo.equipoId.id==it.equipoId.id){
                    equipo
                }else
                    it

            }
        }

    }

}