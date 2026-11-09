package ies.sequeros.liga.application.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.infraestructure.equipos.EquipoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetAllEquiposUseCase (private val repository: EquipoRepository) {
    operator fun invoke(): Flow<List<Equipo>> {
        return repository.getAll()
    }
}