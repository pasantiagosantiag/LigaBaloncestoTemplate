package ies.sequeros.liga.infraestructure.equipos

import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.domain.valueobjects.EquipoId
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path.Companion.toPath

import kotlinx.serialization.encodeToString


    open abstract class InFileEquipoRepository( filePath: String) : InMemoryEquipoRepository() {

        protected val path = filePath.toPath()
        private val reloadCache: MutableStateFlow<Boolean> = MutableStateFlow(true)

        private val json = Json { ignoreUnknownKeys = true }

        override  fun getAll(): Flow<List<Equipo>> {
            return this.items.asFlow().onStart {
                if (reloadCache.value) {
                    reloadCache.value = false // Bloqueamos accesos simultáneos
                    try {
                        val fromFile = loadFromFile() // Función suspend que lee el archivo
                        items.update {
                            fromFile
                        }    // Actualizamos el flujo en memoria
                    } catch (e: Exception) {
                        reloadCache.value = true     // Si falla, permitimos reintentar en el futuro
                    }
                }
                //lo que se encuentra en el start se ejecuta en segundo plano de forma segura sin bloquear el hilo principal
            }.flowOn(Dispatchers.Default)
        }

        override suspend fun getByEquipoId(equipoId: EquipoId): Equipo? {
            return items.value.firstOrNull { it.equipoId == equipoId }
        }

        protected abstract  suspend fun saveToFile()
        protected abstract suspend fun loadFromFile(): List<Equipo>
        override suspend fun save(equipo: Equipo): Equipo {

            items.update {
                if(it.find { it.equipoId.value == equipo.equipoId.value }==null){
                    it+ equipo
                }else {
                    it.map {
                        if (equipo.equipoId.id == it.equipoId.id) {
                            equipo
                        } else
                            it
                    }
                }
            }
            this.saveToFile()
            return equipo
        }

        override suspend fun delete(equipoId: EquipoId) {
            items.update {
                it.filter {
                    equipoId.id!=it.equipoId.id
                }
            }
            this.saveToFile()

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
            this.saveToFile()
        }
        fun reload(){
            reloadCache.update { true }
        }
    }
