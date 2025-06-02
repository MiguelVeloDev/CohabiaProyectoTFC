package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.screens.Compras.ProductoItem
import com.example.cohabiaproject.presentation.ui.viewmodel.CategoriaViewModel
import com.example.cohabiaproject.presentation.ui.viewmodel.ProductoViewModel
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.coloresTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoriasScreen(
    navController: NavController
) {
    var nombre by remember { mutableStateOf("") }
    val categoriaViewModel: CategoriaViewModel = koinViewModel()
    val categorias by categoriaViewModel.categorias.collectAsState()
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Categorías",
                navController = navController
            )
        }

    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(16.dp),
        ) {
            TextField(
                value = nombre,
                onValueChange = { if (it.length <= 20) nombre = it},
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = coloresTextField()
            )

            Spacer(modifier = Modifier.height(34.dp))

            Button(
                onClick = {
                    val nuevaCategoria = Categoria(nombre = nombre)
                    categoriaViewModel.save(nuevaCategoria)
                    nombre = ""
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEE7A16),
                    contentColor = Color.White
            ),
                enabled = nombre.isNotBlank()
            ) {
                Text(text = "Guardar categoría")
                    }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = "Categorías",
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(categorias) { categoria ->
                    CategoriaItem(categoria = categoria, categoriaViewModel = categoriaViewModel)
                }
            }
        }
    }
}
@Composable
fun CategoriaItem(categoria: Categoria, categoriaViewModel: CategoriaViewModel) {
    var marcado = remember { mutableStateOf(false) }
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
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = categoria.nombre, fontSize = 16.sp, fontWeight = FontWeight.Medium,style = if(marcado.value){TextStyle(textDecoration = TextDecoration.LineThrough)}else{TextStyle()})

            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = { marcado.value = !marcado.value }) {
                    Icon(
                        imageVector = if (marcado.value) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = "Borrar producto",
                        tint = RojoCompras
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

                            IconButton(onClick = { categoriaViewModel.borrar(categoria.id) }) {
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
