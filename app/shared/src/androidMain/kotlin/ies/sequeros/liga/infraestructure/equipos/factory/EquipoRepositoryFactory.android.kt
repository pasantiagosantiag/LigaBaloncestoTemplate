package ies.sequeros.liga.infraestructure.equipos.factory

import android.content.Context
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.infraestructure.equipos.AndroidInFileEquipoRepository
import ies.sequeros.liga.infraestructure.equipos.InMemoryEquipoRepository

actual class EquipoRepositoryFactory actual constructor() {

    private lateinit var context: android.content.Context
    fun setContext(context: Context) = apply { this.context = context }

    actual fun createRepository(equipoRepositoryConfig: EquipoRepositoryConfig): EquipoRepository {

       when (equipoRepositoryConfig) {
           is EquipoRepositoryConfig.File -> {
               if (equipoRepositoryConfig is EquipoRepositoryConfig.File) {
                   check(::context.isInitialized) {
                       "Android Context must be initialized using setContext() before creating a File repository."
                   }
               }
               return AndroidInFileEquipoRepository(context,equipoRepositoryConfig.filePath)
           }

           is EquipoRepositoryConfig.BBDD -> throw UnsupportedOperationException("El repositorio de base de datos no está soportado en Android actualmente.")
           EquipoRepositoryConfig.Memory -> return InMemoryEquipoRepository()
       }
    }
}



