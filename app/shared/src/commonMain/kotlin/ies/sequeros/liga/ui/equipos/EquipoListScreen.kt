package ies.sequeros.liga.ui.equipos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ies.sequeros.liga.domain.Equipo
import kotlinx.coroutines.launch

@Composable
fun EquipoListScreen(
    modifier: Modifier = Modifier,
    items: List<Equipo>,
    selectedItem: Equipo? = null,
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit = {},
    onSelected: (item: Equipo) -> Unit,
    onDelete: (item: Equipo) -> Unit,
    onNew: () -> Unit,
    placeOrder: String = ""
) {

    Column(modifier = modifier.fillMaxWidth()) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        // Cuadro de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {

                onSearchQueryChanged(it)
            }, // Se comunica hacia arriba
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp).weight(1f),
            //placeholder = { "${placeOrder}" },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
            },
            singleLine = true
        )
        IconButton(
            modifier = Modifier.fillMaxWidth().weight(0.10f).padding(8.dp).weight(1f),
            onClick = {
                onNew()
            }
        ){
            Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir equipo")

        }
    }
        Spacer(modifier = Modifier.height(4.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp),
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size) {
                var cardModifier = Modifier.padding(4.dp).align(Alignment.CenterHorizontally)
                if (selectedItem != null
                    &&
                    selectedItem.equipoId.id == items[it].equipoId.id
                ) {
                    cardModifier = cardModifier.border(2.dp, Color.Black)
                }
                OutlinedCard(
                    modifier = cardModifier,
                ) {
                    Column (
                        modifier = Modifier.fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        AsyncImage(
                            model = items[it].imagenEquipo.url,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = items[it].nombreEquipo.name,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,

                            modifier = Modifier.fillMaxSize().wrapContentHeight(),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                        IconButton(onClick = {
                            onSelected(items[it])
                        }) {
                            Icon(
                                imageVector = Icons.Default.TouchApp,
                                contentDescription = "Seleccionar"
                            )
                        }
                        IconButton(onClick = {
                            onDelete(items[it])

                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar"
                            )
                        }
                    }
                }
                }

            }
        }

    }
}