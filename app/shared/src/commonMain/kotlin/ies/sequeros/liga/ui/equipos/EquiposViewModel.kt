package ies.sequeros.liga.ui.equipos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.liga.application.equipos.GetAllEquiposUseCase
import ies.sequeros.liga.application.equipos.RemoveEquipoUseCase
import ies.sequeros.liga.domain.Equipo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.uuid.Uuid


class EquiposViewModel(
    private val getAllEquiposUseCase: GetAllEquiposUseCase,
    private val removeEquiposUseCase: RemoveEquipoUseCase
) : ViewModel() {
    //se transforma en un stateflow
    private val equipoList: StateFlow<List<Equipo>> = getAllEquiposUseCase.invoke()
        .stateIn(
            scope = viewModelScope, // Vinculado al ciclo de vida del ViewModel
            started = SharingStarted.WhileSubscribed(5000), // Optimiza la memoria al pausar si la app va a segundo plano
            initialValue = emptyList() // Estado inicial mientras cargan los datos
        )
    private val _selected: MutableStateFlow<Equipo?> = MutableStateFlow(null)
    val selected: StateFlow<Equipo?> = _selected
    private val _searchFilter: MutableStateFlow<String> = MutableStateFlow("")
    val searchFilter: StateFlow<String> = _searchFilter
    val items = combine(equipoList, _searchFilter) { items, searchFilter ->
        if (searchFilter.isBlank()) {
            items
        } else {
            items.filter {
                it.nombreEquipo.name.contains(searchFilter, ignoreCase = true) }
        }
    }.stateIn(
        scope = viewModelScope, // El scope del ViewModel, gestor de corrutinas,operaciones sencillas
        //se ejecuta en el hilo principal
        started = SharingStarted.WhileSubscribed(5000), // Mantiene activo el flujo 5s tras perder observadores
        initialValue = emptyList() // Valor inicial mientras emite el caso de uso
    )
    fun remove(uuid: Uuid) {
        viewModelScope.launch {
            //se pasa a ejecutar en el dispatcher default
            //se modifica el flow, con lo que se provoca una recomposición
           //  withContext(Dispatchers.Default) {
                removeEquiposUseCase(uuid)
            //}
        }
    }
    fun onSearchQueryChanged(query: String) {
        _searchFilter.value=  query
    }
    fun setSelectedEquipo(equipo: Equipo) {
        this._selected.update { equipo }
    }

}