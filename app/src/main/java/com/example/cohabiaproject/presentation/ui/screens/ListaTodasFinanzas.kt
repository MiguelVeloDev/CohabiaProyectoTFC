package com.example.cohabiaproject.presentation.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.MoneyOffCsred
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.cohabiaproject.presentation.ui.components.ListaVaciaPlaceholder
import com.example.cohabiaproject.presentation.ui.components.TopAppBarConFlecha
import com.example.cohabiaproject.presentation.ui.viewmodel.FinanzasViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListaFinanzas(navController: NavController) {
    val finanzasViewModel: FinanzasViewModel = koinViewModel()
    val gastos by finanzasViewModel.finanzas.collectAsState(emptyList())

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBarConFlecha(
                titulo = "Lista de gastos",
                navController = navController
            )
        }
    ) { innerPadding ->
        if (gastos.isEmpty()) {
            ListaVaciaPlaceholder(
                icono = Icons.TwoTone.MoneyOffCsred,
                texto = " gastos"

            )
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(gastos) { gasto ->
                    GastoItem(gasto = gasto, navController = navController)
                }
            }
        }
    }
}