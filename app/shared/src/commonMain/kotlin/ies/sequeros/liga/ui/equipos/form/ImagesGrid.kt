package ies.sequeros.liga.ui.equipos.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ies.sequeros.liga.domain.valueobjects.ImageURL

@Composable
fun ImagesGrid(
    modifier: Modifier = Modifier,
    imagenes: List<ImageURL>,
    selectedImage: ImageURL?,
    onSelected: (ImageURL) -> Unit // Recibe la imagen seleccionada
) {
    LazyVerticalGrid(
        // Grid adaptativo: calcula cuántas columnas caben según el tamaño mínimo (ej. 110.dp por tarjeta)
        columns = GridCells.Adaptive(minSize = 110.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(imagenes) { imagen ->
            val isSelected = imagen == selectedImage

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Hace que cada celda sea cuadrada
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable { onSelected(imagen) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected)
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    else
                        MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                     AsyncImage(
                        model = imagen.url,
                         contentDescription = imagen.name,
                         modifier = Modifier.weight(1f).fillMaxWidth().clip(MaterialTheme.shapes.small),
                         contentScale = ContentScale.Crop
                     )



                    Spacer(modifier = Modifier.height(4.dp))

                    // Nombre de la imagen
                    Text(
                        text = imagen.name,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}