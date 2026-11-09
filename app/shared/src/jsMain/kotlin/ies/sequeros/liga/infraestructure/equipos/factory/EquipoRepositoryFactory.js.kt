package ies.sequeros.liga.infraestructure.equipos.factory

import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.infraestructure.equipos.InMemoryEquipoRepository

actual class EquipoRepositoryFactory actual constructor() {
    actual fun createRepository(equipoRepositoryConfig: EquipoRepositoryConfig): EquipoRepository {
        when (equipoRepositoryConfig) {
            is EquipoRepositoryConfig.File -> throw UnsupportedOperationException("El repositorio en ficheros no está soportado en Web JS.")


            is EquipoRepositoryConfig.BBDD -> throw UnsupportedOperationException("El repositorio de base de datos no está soportado en Web JS.")
            EquipoRepositoryConfig.Memory -> return InMemoryEquipoRepository()
        }
    }
}