package ies.sequeros.liga.infraestructure.equipos.factory

sealed interface EquipoRepositoryConfig {
    data object Memory : EquipoRepositoryConfig
    data class File(val filePath: String) : EquipoRepositoryConfig
    data class BBDD(val conexionString: String) : EquipoRepositoryConfig
}