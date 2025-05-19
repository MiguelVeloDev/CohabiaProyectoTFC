package com.example.cohabiaproject.presentation.ui.viewmodel

import com.example.cohabiaproject.domain.model.Casa
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.data.source.remote.CasaFirestoreRepository
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.repository.CasaRepository
import com.example.cohabiaproject.domain.repository.usecases.SaveCasaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CasaViewModel(
    private val repository: CasaRepository,
    private val saveCasaUseCase: SaveCasaUseCase,
) : ViewModel() {

    private val _casa = MutableStateFlow<Casa?>(null)
    val casa: StateFlow<Casa?> = _casa

    init {
        cargarCasaUsuario()
    }

    fun cargarCasaUsuario() {
        viewModelScope.launch {
            _casa.value = repository.getCasaDelUsuario()
        }
    }

    suspend fun guardarCasa(nombre: String): String {
        val id = saveCasaUseCase.invoke(nombre)
        _casa.value = Casa(id = id, nombre = nombre, miembros = listOf())
        return id
    }


    fun actualizarCasa(casa: Casa) {
        viewModelScope.launch {
            repository.updateCasa(casa)
            _casa.value = casa
        }
    }

    fun getById(){
        viewModelScope.launch {
            _casa.value = repository.getById(Sesion.casaId)
        }
    }

    fun eliminarCasa(casaId: String) {
        viewModelScope.launch {
            repository.deleteCasa(casaId)
            _casa.value = null
        }
    }

    fun unirmeCasa(codigo:String){
        viewModelScope.launch {
            repository.unirmeCasa(codigo)
        }

    }
}