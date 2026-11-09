package ies.sequeros.liga.ui.equipos.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.liga.application.equipos.AddEquipoUseCase
import ies.sequeros.liga.domain.Equipo
import ies.sequeros.liga.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class EquipoFormViewModel(
    private val addEquipoUseCase: AddEquipoUseCase,
    private val imageRepository: ImageRepository
) : ViewModel() {
    //se transforma en un stateflow
    private val _formState = MutableStateFlow(EquipoFormState())
    val formState: StateFlow<EquipoFormState> = _formState
    val images=imageRepository.getAll()
    public fun setEquipo(equipo: Equipo) {
        _formState.update {
            EquipoFormState.fromDomain(equipo)
        }
    }

    fun resetForm() {
        _formState.update { EquipoFormState() }
    }

    fun add() {
        if (!_formState.value.isValid) return

        viewModelScope.launch {
            _formState.update { it.copy(isLoading = true, errorMessage = null) }
            //se pasa a ejecutar en el dispatcher default
            //se modifica el flow, con lo que se provoca una recomposición
            try {
                //withContext(Dispatchers.Default) {
                    addEquipoUseCase(
                        formState.value.equipoId,
                        formState.value.nombreEquipo,
                        formState.value.imagenUrl,
                        formState.value.imagenNombre
                    )
                //}
            } catch (e: Exception) {
                _formState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            } finally {
                _formState.update { it.copy(isLoading = false) }
            }
        }
    }

    public fun onImagenChange(imageName: String) {
        _formState.update {
            it.copy(imagenNombre = imageName)
        }
    }

    public fun onNombreChange(nombre: String) {
        _formState.update { it.copy(nombreEquipo = nombre) }
    }

    public fun onUrlChange(url: String) {
        _formState.update { it.copy(imagenUrl = url) }
    }

}