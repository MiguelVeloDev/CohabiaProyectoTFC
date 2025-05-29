package com.example.cohabiaproject.presentation.ui.screens.Compras
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.CategoriaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import com.example.cohabiaproject.ui.theme.FondoTextField
import com.example.cohabiaproject.ui.theme.NaranjaPrincipal
import com.example.cohabiaproject.ui.theme.RojoCompras
import org.koin.androidx.compose.koinViewModel
import java.util.UUID
import kotlin.collections.component1
import kotlin.collections.component2


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
    val categoriaViewModel : CategoriaViewModel = koinViewModel()
    val categorias by categoriaViewModel.nombresCategorias.collectAsState(
        initial = emptyList())
    var expandido by remember { mutableStateOf(false) }
    val productosRecurrentes by productoViewModel.productosRecurrentes.collectAsState()
    val productosPorCategoria = productosRecurrentes.groupBy { it.categoria }
    var comentario by remember { mutableStateOf("") }


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
                        categorias.forEach { opcion ->
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
                    onClick = { navController.navigate("categorias") },
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
                TextField(
                    value = comentario,
                    onValueChange = {comentario = it},
                    label = { Text("Comentario") },

                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = FondoTextField,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )
                Checkbox(checked = recurrente, onCheckedChange = { recurrente = it }, colors = CheckboxDefaults.colors(
                    checkedColor = RojoCompras,
                    uncheckedColor = RojoCompras
                )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recurrente")
            }

            Button(
                onClick = {
                    val nuevoProducto = Producto(
                        nombre = nombre,
                        categoria = categoria,
                        recurrente = recurrente,
                        enLista = true,
                        comentario = comentario,
                    )
                    productoViewModel.save(nuevoProducto)
                    navController.popBackStack()
                },
                enabled = nombre.isNotBlank() && categoria.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoCompras,
                    contentColor = Color.White
                )
            ) {
                Text("Guardar producto")
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),

                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                productosPorCategoria.forEach { (categoria, productos) ->
                    item {
                        Text(
                            text = categoria,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = RojoCompras
                        )
                    }
                    items(productos) { producto ->
                        ProductoRecurrenteItem(producto = producto, productoViewModel = productoViewModel)
                    }
                }

            }
        }
    }
}


@Composable
fun ProductoRecurrenteItem(producto: Producto, productoViewModel: ProductoViewModel) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,

                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    productoViewModel.update(producto.copy(enLista = !producto.enLista))
                }) {
                    Icon(
                        imageVector =if (producto.enLista) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = "Marcar producto",
                        tint = NaranjaPrincipal
                    )
                }

                AnimatedVisibility(visible = expanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF3F3F3))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = { productoViewModel.borrar(producto.id) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Borrar producto",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
