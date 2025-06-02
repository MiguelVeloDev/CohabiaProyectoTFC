package com.example.cohabiaproject.presentation.ui.screens



import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.TarjetaCasa
import com.example.cohabiaproject.presentation.ui.components.TarjetaCompras
import com.example.cohabiaproject.presentation.ui.components.TarjetaElectrodomestico
import com.example.cohabiaproject.presentation.ui.components.TarjetaFuncionando
import com.example.cohabiaproject.presentation.ui.components.TarjetaTareas
import com.example.cohabiaproject.presentation.ui.components.UltimosEventosComponente
import com.example.cohabiaproject.presentation.ui.viewmodel.CasaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.TareaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.UsuarioViewModel
import com.example.cohabiaproject.ui.theme.LoadingAnimation
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val casaId = remember { mutableStateOf("") }
    val userId = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        Sesion.cargarSesion()
        casaId.value = Sesion.casaId
        userId.value = Sesion.userId
        Log.d("SesionMainScreen", "${userId.value} ${casaId.value}")
    }
    if (casaId.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation()
        }
    } else {
        val electrodomesticoViewModel: ElectrodomesticoViewModel = koinViewModel()
        val electrodomesticos by electrodomesticoViewModel.electrodomesticos.collectAsState()

        val electrodomesticosEnEjecucion by remember {
            derivedStateOf {
                electrodomesticos.filter { it.isRunning }
            }
        }
        val casaViewModel: CasaViewModel = koinViewModel()
        val casa by casaViewModel.casa.collectAsState()
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
        val tareasViewModel: TareaViewModel = koinViewModel()
        val tareasHoy by tareasViewModel.tareasHoy.collectAsState()
        val hayTareasHoy = remember(tareasHoy) { tareasHoy.isNotEmpty() }
        val eventosViewModel: EventoViewModel = koinViewModel()
        val eventos by eventosViewModel.eventos.collectAsState()
        val ultEventos = eventosViewModel.ultimosTresEventos
        val usuarioViewModel: UsuarioViewModel = koinViewModel()
        val numUsuarios by usuarioViewModel.numUsuarios.collectAsState()





        Scaffold(
            containerColor = Color.White,
            topBar = { MyTopAppBar(navController, "") },
            bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = casa?.nombre ?: "",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
                        color = NaranjaPrincipal,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(color =NaranjaPrincipal, thickness = 1.dp)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF6F5F5)),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            TarjetaCasa(numUsuarios = numUsuarios, navController = navController)
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TarjetaCompras(
                                    hayTareasHoy = hayTareasHoy,
                                    numeroTareas = tareasHoy.size,
                                    navController = navController,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                )
                                TarjetaTareas(
                                    hayTareasHoy = hayTareasHoy,
                                    numeroTareas = tareasHoy.size,
                                    navController = navController,
                                    modifier = Modifier
                                        .weight(2f)
                                        .aspectRatio(2f)
                                )

                            }
                        }

                        item {
                            TarjetaFuncionando(
                                electrodomesticosEnFuncionamiento = electrodomesticosEnEjecucion, navController = navController
                            )
                        }

                        item {
                            UltimosEventosComponente(
                                eventos = ultEventos,
                                navController = navController
                            )
                        }




                    }
                }
            }
        }
    }
    }