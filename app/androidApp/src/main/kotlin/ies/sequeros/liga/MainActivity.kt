package ies.sequeros.liga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ies.sequeros.liga.infraestructure.equipos.factory.EquipoRepositoryConfig
import ies.sequeros.liga.infraestructure.equipos.factory.EquipoRepositoryFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

             val equipoRepository = remember(context) {
                val equipoRepositoryFactory = EquipoRepositoryFactory()
                equipoRepositoryFactory.setContext(context)

                 equipoRepositoryFactory.createRepository(EquipoRepositoryConfig.File("equipo.json"))
            }
            App(equipoRepository = equipoRepository)
        }
    }
}

/*@Preview
@Composable
fun AppAndroidPreview() {
    App(EquipoRepositoryConfig.Memory)
}*/