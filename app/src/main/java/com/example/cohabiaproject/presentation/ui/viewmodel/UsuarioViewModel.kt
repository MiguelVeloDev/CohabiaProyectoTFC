package com.example.cohabiaproject.presentation.ui.viewmodel

import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.GetUsuarioUseCase


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.DeleteUsuarioUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.GetUsuarioByIdUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.SaveUsuarioUseCase
import com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases.UpdateUsuarioUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel(
    private val getUsuarioUseCase: GetUsuarioUseCase,
    private val saveUsuarioUseCase: SaveUsuarioUseCase,
    private val updateUsuarioUseCase: UpdateUsuarioUseCase,
    private val deleteUsuarioUseCase: DeleteUsuarioUseCase,
    private val getUsuarioByIdUseCase: GetUsuarioByIdUseCase

) : ViewModel() {

    private val _usuarioRegistrado = MutableStateFlow<Usuario?>(null)
    val usuarioRegistrado = _usuarioRegistrado.asStateFlow()

    fun obtenerUsuarioRegistrado() {
        viewModelScope.launch {
            val usuario = getUsuarioByIdUseCase(Sesion.userId)
            _usuarioRegistrado.value = usuario
        }
    }


    private var _usuarios = getUsuarioUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios


    fun save(usuario: Usuario) {
        viewModelScope.launch {
            saveUsuarioUseCase(usuario)
        }
    }

    fun update(usuario: Usuario) {
        viewModelScope.launch {
            updateUsuarioUseCase(usuario)
        }
    }

    suspend fun getById(usuarioId: String): Usuario? {
        return withContext(Dispatchers.IO) {
            val usuario = getUsuarioByIdUseCase(usuarioId)
            Log.d("UsuarioViewModel", usuario.toString())
            usuario
        }
    }



    fun borrar(usuario: Usuario) {
        viewModelScope.launch {
            deleteUsuarioUseCase(usuario)
        }
    }




    fun compartir(usuario: Usuario){
        Log.d("UsuarioViewModel", "Esto hará algo")
    }
}