package ies.sequeros.liga

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ies.sequeros.liga.infraestructure.equipos.factory.EquipoRepositoryConfig
import ies.sequeros.liga.infraestructure.equipos.factory.EquipoRepositoryFactory

fun main() = application {


        val equipoRepositoryFactory = EquipoRepositoryFactory()
       val equipoRepository= equipoRepositoryFactory.createRepository(EquipoRepositoryConfig.File("./equipo.json"))


    Window(
        onCloseRequest = ::exitApplication,
        title = "LigaBaloncesto",
    ) {
        App(equipoRepository = equipoRepository)
    }
}