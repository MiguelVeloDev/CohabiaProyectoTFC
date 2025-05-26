package com.example.cohabiaproject.presentation.ui.screens.Compras
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.RojoCompras
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
    val opciones = listOf("Lacteos", "Frutas", "Verduras")
    var expandido by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Añadir producto",
                navController = navController
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
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = Color.Black,
                    unfocusedContainerColor = FondoTextField,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = { expandido = !expandido }
                ) {
                    TextField(
                        value = categoria,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Categoría") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
                        },
                        modifier = Modifier.menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = FondoTextField,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    ExposedDropdownMenu(
                        containerColor = Color.White,
                        expanded = expandido,
                        onDismissRequest = { expandido = false }
                    ) {
                        opciones.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion) },
                                onClick = {
                                    categoria = opcion
                                    expandido = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircleOutline,
                        tint = Color.Gray,
                        contentDescription = "Borrar nota"
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = recurrente, onCheckedChange = { recurrente = it }, colors = CheckboxDefaults.colors(
                    checkedColor = RojoCompras,
                    uncheckedColor = RojoCompras
                )
                )
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
                enabled = nombre.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoCompras,
                    contentColor = Color.White
                )
            ) {
                Text("Guardar producto")
            }
        }
    }
}

