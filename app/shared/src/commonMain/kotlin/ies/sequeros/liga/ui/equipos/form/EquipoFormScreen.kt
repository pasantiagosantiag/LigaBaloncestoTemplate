package ies.sequeros.liga.ui.equipos.form

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EquipoFormScreen(
    viewModel: EquipoFormViewModel,

    onEquipoAdded: () -> Unit // Callback para navegar o cerrar la pantalla al guardar con éxito
) {
    // Recolectamos el estado del ViewModel de forma reactiva
    val state by viewModel.formState.collectAsState()
    val images by viewModel.images.collectAsState(initial = emptyList())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.equipoId?.let { "Editar equipo" } ?: "Crear equipo",
                style = MaterialTheme.typography.headlineMedium
            )

            // Campo: Nombre del Equipo
            OutlinedTextField(
                value = state.nombreEquipo,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre del Equipo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            )

            // Campo: Nombre de la Imagen
            OutlinedTextField(
                value = state.imagenNombre,
                onValueChange = { viewModel.onImagenChange(it) },
                label = { Text("Nombre / Descripción de la Imagen") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            )

            ImagesGrid(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(8.dp),
                imagenes = images,
                selectedImage = null,
                onSelected = {
                    viewModel.onUrlChange(it.url)
                }
            )
            // Campo: URL de la Imagen
            OutlinedTextField(
                value = state.imagenUrl,
                onValueChange = { viewModel.onUrlChange(it) },
                label = { Text("URL de la Imagen (https://...)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            )

            // Mensaje de error general si ocurre alguno
            if (state.errorMessage != null) {
                Text(
                    text = state.errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de Enviar / Guardar
            Button(
                onClick = {
                    viewModel.add()
                    viewModel.resetForm()
                    onEquipoAdded()
                },
                // El botón solo se habilita si el formulario es válido y no está cargando
                enabled = state.isValid && !state.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar Equipo")
                }
            }
        }
    }
}