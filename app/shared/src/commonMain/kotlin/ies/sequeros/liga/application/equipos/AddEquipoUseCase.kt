package ies.sequeros.liga.application.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.domain.valueobjects.EquipoId
import ies.sequeros.liga.domain.valueobjects.ImageURL
import ies.sequeros.liga.domain.valueobjects.NombreEquipo
import kotlin.uuid.Uuid

class AddEquipoUseCase (private val equipoRepository: EquipoRepository) {
    suspend operator fun invoke(id: String?=null, nombre: String, imageName:String, url:String) {

        val equipo= Equipo(
            equipoId = id?.let { EquipoId.from(Uuid.parse(it)) } ?: EquipoId.random(),
            nombreEquipo = NombreEquipo( nombre),
            imagenEquipo = ImageURL(imageName,url)
        )
        equipoRepository.save(equipo)

    }
}