package ies.sequeros.liga.infraestructure.equipos.factory

import ies.sequeros.liga.domain.repositories.EquipoRepository

 expect class EquipoRepositoryFactory() {

    fun createRepository(equipoRepositoryConfig: EquipoRepositoryConfig): EquipoRepository
}