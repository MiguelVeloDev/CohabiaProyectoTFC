package com.example.cohabiaproject.presentation.navigation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Login : Screen("login")
    data object Registro : Screen("registro")
    data object Main : Screen("main")
    data object NuevoElectrodomestico : Screen("nuevoElectrodomestico")
    data object ListaElectrodomesticos : Screen("listaElectrodomesticos")
    data object NuevoPrograma : Screen("nuevoPrograma/{nombre}/{tipoElectrodomestico}") {
        fun createRoute(nombre: String, tipo: String): String = "nuevoPrograma/$nombre/$tipo"
    }
    data object NotasScreen : Screen("notas")

    data object MostrarNotaScreen : Screen("mostrarNota/{id}") {
        fun createRoute(id: String): String = "mostrarNota/$id"
    }

    data object FinanzasScreen : Screen("finanzas")


    data object NuevoGastoScreen : Screen("nuevoGasto/{usuarioPaga}") {
        fun createRoute(usuarioPaga: String): String = "nuevoGasto/$usuarioPaga"
    }
    data object DetallesGasto : Screen("detalleGasto/{id}")
    data object EditarGasto : Screen("editarGasto/{id}")
    data object CrearCasa : Screen("crearCasa")
    data object MiPerfil : Screen("miPerfil")
    data object CodigoCasa : Screen("codigoCasa")
    data object UnirmeCasa : Screen("unirmeCasa")
    data object EleccionCasa : Screen("eleccionCasa")
    data object MisDatos : Screen("misDatos")
    data object SeleccionUsuarioGasto : Screen("seleccionUsuarioGasto")
    data object EventosScreen : Screen("eventos")
    data object VerificacionCorreo : Screen("verificacionCorreo")
    data object AnadirPrograma : Screen("anadirPrograma/{id}") {
        fun createRoute(id: String): String = "anadirPrograma/$id"
    }
    data object Compras : Screen("compras")
    data object AnadirProducto : Screen("anadirProducto")


    data object Prueba : Screen("prueba")



}