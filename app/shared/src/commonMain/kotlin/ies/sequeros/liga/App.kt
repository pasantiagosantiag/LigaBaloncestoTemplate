package ies.sequeros.liga

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import ies.sequeros.liga.application.equipos.AddEquipoUseCase
import ies.sequeros.liga.application.equipos.GetAllEquiposUseCase
import ies.sequeros.liga.application.equipos.RemoveEquipoUseCase
import ies.sequeros.liga.domain.repositories.EquipoRepository
import ies.sequeros.liga.infraestructure.imagenes.RestImageRepository
import ies.sequeros.liga.ui.equipos.EquipoListScreen
import ies.sequeros.liga.ui.equipos.EquiposViewModel
import ies.sequeros.liga.ui.equipos.form.EquipoFormScreen
import ies.sequeros.liga.ui.equipos.form.EquipoFormViewModel
import ies.sequeros.liga.ui.routes.MainRoutes
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            // Registra todas las pantallas/rutas de tu sealed interface
            subclass(MainRoutes.ListadoEquipos::class, MainRoutes.ListadoEquipos.serializer())
            subclass(MainRoutes.DetalleEquipo::class, MainRoutes.DetalleEquipo.serializer())

        }
    }
}

@Composable

fun App(equipoRepository: EquipoRepository) {
    val scope = rememberCoroutineScope()
    val backStack = rememberNavBackStack(navConfig, MainRoutes.ListadoEquipos)
    val equiposViewModel = viewModel {
        val getAllEquiposUseCase = GetAllEquiposUseCase(equipoRepository)
        val removeEquipoUseCase = RemoveEquipoUseCase(equipoRepository)
        EquiposViewModel(getAllEquiposUseCase, removeEquipoUseCase)
    }
    val equipoFormViewModel = viewModel {
        val addEquipoUseCase = AddEquipoUseCase(equipoRepository)
        val imageRepository = RestImageRepository()
        EquipoFormViewModel(addEquipoUseCase, imageRepository)
    }
    val items by equiposViewModel.items.collectAsState()
    val searchQuery by equiposViewModel.searchFilter.collectAsState()
    val selectedItem by equiposViewModel.selected.collectAsState()

    MaterialTheme {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row() {

                NavDisplay(backStack = backStack, onBack = { backStack.removeLastOrNull() }, entryProvider = { key ->
                    when (key) {

                        is MainRoutes.ListadoEquipos -> NavEntry(key) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                EquipoListScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    items = items,
                                    searchQuery = searchQuery,
                                    onSearchQueryChanged = {
                                        equiposViewModel.onSearchQueryChanged(it)
                                    },
                                    selectedItem = selectedItem,
                                    onNew = {
                                        equipoFormViewModel.resetForm()
                                        backStack.add(MainRoutes.DetalleEquipo())

                                    },
                                    onSelected = {
                                        equipoFormViewModel.setEquipo(it)
                                        backStack.add(MainRoutes.DetalleEquipo(it.equipoId.id))
                                        // equiposViewModel.setSelectedEquipo(it)
                                    },
                                    onDelete = { equiposViewModel.remove(it.equipoId.value) },
                                    placeOrder = "Buscar equipo"
                                )
                            }
                        }

                        is MainRoutes.DetalleEquipo -> NavEntry(key) {
                            EquipoFormScreen(
                                equipoFormViewModel,

                                onEquipoAdded = {
                                    if (backStack.size > 1) {
                                        backStack.removeLast()
                                    }
                                }
                            )
                        }

                        else -> {
                            error("Unknown route: $key")
                        }
                    }
                })
            }
        }
    }
}
