package ies.sequeros.liga.application.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.domain.valueobjects.EquipoId
import ies.sequeros.liga.domain.valueobjects.ImageURL
import ies.sequeros.liga.domain.valueobjects.NombreEquipo
import kotlin.uuid.Uuid

class RemoveEquipoUseCase (private val equipoRepository: EquipoRepository) {
    suspend operator fun invoke(uuid: Uuid) {
          equipoRepository.delete(EquipoId.from(uuid))

    }
}