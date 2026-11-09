package ies.sequeros.liga.application.imagesequipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.domain.repositories.ImageRepository
import ies.sequeros.liga.domain.valueobjects.ImageURL
import ies.sequeros.liga.infraestructure.equipos.EquipoDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetAllImagenesEquipoUseCase (private val repository: ImageRepository) {
     suspend fun invoke(): Flow<List<ImageURL>> {
        return repository.getAll()
    }
}