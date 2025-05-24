package com.example.cohabiaproject.presentation.ui.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.presentation.navigation.navigation.Screen
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.components.NuevoElementoTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.ElectrodomesticoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.EventoViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.NotaViewModel
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.koinViewModel


@Composable
fun MostrarNotaScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    notaID: String,

    ) {
    val notaViewModel: NotaViewModel = koinViewModel()
    val listaNotas by notaViewModel.notas.collectAsState(emptyList())
    val nota = remember(listaNotas) { listaNotas.find { it.id == notaID } }
    val contenidoNota = remember(nota) {
        MutableStateFlow(TextFieldValue(nota?.contenido ?: ""))
    }
    val contenidoNotaState = contenidoNota.collectAsState()
    val tituloNota = remember(nota) { mutableStateOf(nota?.titulo ?: "Titulo")}
    val eventoViewModel: EventoViewModel = koinViewModel()


    LaunchedEffect(nota?.id) {
        if (nota != null) {
            contenidoNota
                .debounce(1000)
                .collectLatest { valor ->
                    notaViewModel.update(
                        nota.copy(contenido = valor.text, titulo = tituloNota.value)
                    )
                }
        }
    }




    Scaffold(
        containerColor = Color.White,
        topBar = {
            NuevoElementoTopAppBar(
                titulo = if(notaID=="nuevaNota") "Nueva nota" else "Editar nota",
                textoBoton = "Guardar",
                navController = navController,
                accion ={
                    if(notaID=="nuevaNota"){
                        notaViewModel.save(Nota(titulo = tituloNota.value, contenido = contenidoNotaState.value.text))
                    }
                    navController.navigate("notas");
                    eventoViewModel.save(Evento(tipo = "NOTA", contenido = eventoViewModel.generarMensaje("NOTA", tituloNota.value)))

                },
                enabled = tituloNota.value!="Titulo" && contenidoNotaState.value.text!="",
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(10.dp)
                    .weight(1f)


            ) {
                Column {

                    BasicTextField(
                        value = tituloNota.value,
                        onValueChange = { tituloNota.value = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        decorationBox = { innerTextField ->
                            innerTextField()
                        }



                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BasicTextField(
                        value = contenidoNotaState.value,
                        onValueChange = { contenidoNota.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        decorationBox = { innerTextField ->
                            if (contenidoNotaState.value.text.isEmpty()) {
                                Text("Escribe tu nota aquí...", color = Color.White)
                            }
                            innerTextField()


                        }

                    )
                }
            }

        }
    }
}
