package com.example.cohabiaproject.presentation.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.presentation.ui.components.BottomNavBar
import com.example.cohabiaproject.presentation.ui.components.MyTopAppBar
import com.example.cohabiaproject.presentation.ui.viewmodel.NotaViewModel
import com.example.cohabiaproject.ui.theme.CohabiaProjectTheme
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class NotasScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CohabiaProjectTheme {
                NotasScreen(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun NotasScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
    val notaViewModel: NotaViewModel = koinViewModel()
    val listaNotas by notaViewModel.notas.collectAsState(emptyList())

    Scaffold(
        topBar = { MyTopAppBar(navController) },
        bottomBar = { BottomNavBar(navController, selectedRoute = currentRoute) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Mis notas", modifier = Modifier.padding(bottom = 8.dp).padding(top = 16.dp), fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Card(
                modifier = Modifier
                    .fillMaxWidth() ,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Nueva nota",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Icono más para nueva nota",
                        tint = Color(0xFF3F9D44),
                        modifier = Modifier
                            .size(32.dp)
                            .clickable(onClick = { navController.navigate("mostrarNota/nuevaNota") })
                    )
                }
                HorizontalDivider(thickness = 2.dp, color = Color.Gray)

            }
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(listaNotas) { nota ->
                    NotaItem(nota = nota, navController = navController, notaViewModel = notaViewModel)
                }
            }
        }
    }
}@Composable
fun NotaItem(
    nota: Nota,
    navController: NavController,
    notaViewModel: NotaViewModel,
) {
    var expanded by remember { mutableStateOf(false) }
    val textColorSecondary = Color.Black.copy(alpha = 0.6f)
    val textColorDisabled = Color.Black.copy(alpha = 0.38f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("mostrarNota/${nota.id}") })
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = nota.titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (!expanded) {
                            Icons.Default.ArrowDropDown
                        } else {
                            Icons.Default.ArrowDropUp
                        },
                        contentDescription = "Mostrar/Ocultar opciones",
                        tint = textColorSecondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = nota.contenido,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = textColorSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(nota.fecha.toDate()),
                fontSize = 12.sp,
                color = textColorDisabled,
                        modifier = Modifier.align(Alignment.End)
            )

            AnimatedVisibility(visible = expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = { notaViewModel.compartir(nota); expanded = false },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            tint = textColorSecondary ,
                                    contentDescription = "Compartir nota"
                        )
                    }
                    IconButton(
                        onClick = { notaViewModel.borrar(nota.id); expanded = false },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            tint = Color.Red,
                            contentDescription = "Borrar nota"
                        )
                    }
                }
            }
        }
    }
}