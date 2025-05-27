package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases.DeleteCategoriaUseCase
import com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases.GetCategoriaUseCase
import com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases.SaveCategoriaUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class CategoriaViewModel(
    private val getCategoriaUseCase: GetCategoriaUseCase,
    private val saveCategoriaUseCase: SaveCategoriaUseCase,
    private val deleteCategoriaUseCase: DeleteCategoriaUseCase
) : ViewModel() {


    private var _categorias = getCategoriaUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias


     var nombresCategorias = categorias.map {
        categorias.value.map { it.nombre }
    }

    fun save(categoria: Categoria) {
        viewModelScope.launch {
            saveCategoriaUseCase(categoria)
        }
    }


    fun borrar(id: String) {
        viewModelScope.launch {
            deleteCategoriaUseCase(id)
        }
    }
}

