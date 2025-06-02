package com.example.cohabiaproject.presentation.navigation.navigation

import AnadirTarea
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.screens.Compras.AnadirProducto
import com.example.cohabiaproject.presentation.ui.screens.AnadirPrograma
import com.example.cohabiaproject.presentation.ui.screens.CasaScreen
import com.example.cohabiaproject.presentation.ui.screens.CategoriasScreen
import com.example.cohabiaproject.presentation.ui.screens.ChatScreen
import com.example.cohabiaproject.presentation.ui.screens.CodigoCasa
import com.example.cohabiaproject.presentation.ui.screens.Compras.Compras
import com.example.cohabiaproject.presentation.ui.screens.CrearCasa
import com.example.cohabiaproject.presentation.ui.screens.DetallesGasto
import com.example.cohabiaproject.presentation.ui.screens.EditarGasto
import com.example.cohabiaproject.presentation.ui.screens.EleccionCasa
import com.example.cohabiaproject.presentation.ui.screens.EventosScreen
import com.example.cohabiaproject.presentation.ui.screens.FinanzasScreen
import com.example.cohabiaproject.presentation.ui.screens.ListaElectrodomesticosScreen
import com.example.cohabiaproject.presentation.ui.screens.ListaFinanzas
import com.example.cohabiaproject.presentation.ui.screens.Login
import com.example.cohabiaproject.presentation.ui.screens.MainScreen
import com.example.cohabiaproject.presentation.ui.screens.MiPerfil
import com.example.cohabiaproject.presentation.ui.screens.MisDatos
import com.example.cohabiaproject.presentation.ui.screens.MostrarNotaScreen
import com.example.cohabiaproject.presentation.ui.screens.NotasScreen
import com.example.cohabiaproject.presentation.ui.screens.NuevoElectrodomestico
import com.example.cohabiaproject.presentation.ui.screens.NuevoGastoScreen
import com.example.cohabiaproject.presentation.ui.screens.NuevoPrograma
import com.example.cohabiaproject.presentation.ui.screens.PantallaInicial
import com.example.cohabiaproject.presentation.ui.screens.Registro
import com.example.cohabiaproject.presentation.ui.screens.SeleccionUsuarioGasto
import com.example.cohabiaproject.presentation.ui.screens.TareasScreen
import com.example.cohabiaproject.presentation.ui.screens.UnirmeCasa
import com.example.cohabiaproject.presentation.ui.screens.VerificacionCorreo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(startDestination: String = Screen.PantallaInicial.route) {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""



    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Login.route) {
            Login(modifier = Modifier, navController)
        }
        composable(Screen.Registro.route) {
            Registro(modifier = Modifier, navController)
        }
        composable(Screen.Main.route) {
            BottomNavBar(navController = navController, currentRoute)
            MainScreen(modifier = Modifier, navController)
        }
        composable(Screen.NuevoElectrodomestico.route) {
            NuevoElectrodomestico(modifier = Modifier, navController)
        }
        composable(Screen.ListaElectrodomesticos.route) {
            ListaElectrodomesticosScreen(modifier = Modifier, navController)
        }

        composable(Screen.NuevoPrograma.route) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val tipo = backStackEntry.arguments?.getString("tipoElectrodomestico") ?: ""
            NuevoPrograma(navController, nombre, tipo)
        }
        composable(Screen.NotasScreen.route) {
            NotasScreen(modifier = Modifier, navController)
        }
        composable(Screen.MostrarNotaScreen.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            MostrarNotaScreen(modifier = Modifier, navController, id)
        }

        composable(Screen.DetallesGasto.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetallesGasto(modifier = Modifier, navController, id)
        }

        composable(Screen.FinanzasScreen.route) {
            FinanzasScreen(modifier = Modifier, navController)
        }

        composable(Screen.NuevoGastoScreen.route) { backStackEntry ->
            val usuarioPaga = backStackEntry.arguments?.getString("usuarioPaga") ?: ""
            NuevoGastoScreen(modifier = Modifier, navController, usuarioPaga)
        }

        composable(Screen.EditarGasto.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            EditarGasto(modifier = Modifier, navController, id)
        }

        composable(Screen.CrearCasa.route) {
            CrearCasa(modifier = Modifier, navController)
        }

        composable(Screen.MiPerfil.route) {
            MiPerfil(navController)
        }

        composable(Screen.CodigoCasa.route) {
            CodigoCasa(modifier = Modifier, navController)
        }
        composable(Screen.UnirmeCasa.route) {
            UnirmeCasa(modifier = Modifier, navController)
        }
        composable(Screen.EleccionCasa.route) {
            EleccionCasa(modifier = Modifier, navController)
        }
        composable(Screen.MisDatos.route) {
            MisDatos(modifier = Modifier, navController)
        }
        composable(Screen.SeleccionUsuarioGasto.route) {
            SeleccionUsuarioGasto(modifier = Modifier, navController)
        }
        composable(Screen.EventosScreen.route) {
            EventosScreen(modifier = Modifier, navController)
        }
        composable(Screen.VerificacionCorreo.route) {
            VerificacionCorreo(navController)
        }
        composable(Screen.AnadirPrograma.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            AnadirPrograma(modifier = Modifier, navController, id)
        }
        composable(Screen.Compras.route) {
            Compras(modifier = Modifier, navController)
        }
        composable(Screen.AnadirProducto.route) {
            AnadirProducto(modifier = Modifier, navController)
        }
        composable(Screen.PantallaInicial.route) {
            PantallaInicial(navController)
        }
        composable(Screen.CategoriaScreen.route) {
            CategoriasScreen(navController)
        }
        composable(Screen.AnadirTarea.route) {
            AnadirTarea(navController)
        }
        composable(Screen.TareasScreen.route) {
            TareasScreen(navController)
        }
        composable(Screen.ChatScreen.route) {
            ChatScreen(navController)
        }
        composable(Screen.CasaScreen.route) {
            CasaScreen(navController)
        }
        composable(Screen.ListaFinanzas.route) {
            ListaFinanzas(navController)
        }


    }
}