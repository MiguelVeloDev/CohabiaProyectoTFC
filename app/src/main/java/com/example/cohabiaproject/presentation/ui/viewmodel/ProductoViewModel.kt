package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.DeleteProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.GetProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.SaveProductoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases.UpdateProductoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class ProductoViewModel(
    private val getProductoUseCase: GetProductoUseCase,
    private val saveProductoUseCase: SaveProductoUseCase,
    private val updateProductoUseCase: UpdateProductoUseCase,
    private val deleteProductoUseCase: DeleteProductoUseCase
) : ViewModel() {


    private var _productos = getProductoUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val productos: StateFlow<List<Producto>> = _productos


    fun save(producto: Producto) {
        viewModelScope.launch {
            saveProductoUseCase(producto)
        }
    }

    fun update(producto: Producto) {
        viewModelScope.launch {
            updateProductoUseCase(producto)
        }
    }

    fun getById(productoId: String): Producto? {
        return productos.value.find { it.id == productoId }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            deleteProductoUseCase(id)
        }
    }


    fun borrarComprados(productos: List<Producto>) {
        viewModelScope.launch {
            productos.forEach { producto ->
                deleteProductoUseCase(producto.id)
            }
        }
    }

    fun compartir(producto: Producto){
        Log.d("ProductoViewModel", "Esto hará algo")
    }
}