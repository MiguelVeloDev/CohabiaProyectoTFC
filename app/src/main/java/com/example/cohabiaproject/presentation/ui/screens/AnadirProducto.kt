package com.example.cohabiaproject.presentation.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.UUID



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnadirProducto(
    modifier: Modifier = Modifier,
    navController: NavController,
    productoViewModel: ProductoViewModel = koinViewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var recurrente by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = recurrente, onCheckedChange = { recurrente = it })
                Spacer(modifier = Modifier.width(8.dp))
                Text("Producto recurrente")
            }

            Button(
                onClick = {
                    val nuevoProducto = Producto(
                        id = UUID.randomUUID().toString(),
                        nombre = nombre,
                        categoria = categoria,
                        recurrente = recurrente
                    )
                    productoViewModel.save(nuevoProducto)
                    navController.popBackStack()
                },
                enabled = nombre.isNotBlank() && categoria.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar producto")
            }
        }
    }
}
