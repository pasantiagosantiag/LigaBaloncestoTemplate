package ies.sequeros.liga.infraestructure.equipos.factory

import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.infraestructure.equipos.DesktopFileEquipoRepository
import ies.sequeros.liga.infraestructure.equipos.InMemoryEquipoRepository

actual class EquipoRepositoryFactory actual constructor() {


    actual fun createRepository(equipoRepositoryConfig: EquipoRepositoryConfig): EquipoRepository {

       when (equipoRepositoryConfig) {
           is EquipoRepositoryConfig.File -> {
               return DesktopFileEquipoRepository(equipoRepositoryConfig.filePath)
           }

           is EquipoRepositoryConfig.BBDD -> throw UnsupportedOperationException("El repositorio de base de datos no está soportado en Android actualmente.")
           EquipoRepositoryConfig.Memory -> return InMemoryEquipoRepository()
       }
    }
}