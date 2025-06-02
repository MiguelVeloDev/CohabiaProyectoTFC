package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Mensaje
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase

import com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases.DeleteMensajeUseCase
import com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases.GetMensajeUseCase
import com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases.SaveMensajeUseCase
import com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases.UpdateMensajeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class MensajeViewModel(
    private val getMensajeUseCase: GetMensajeUseCase,
    private val saveMensajeUseCase: SaveMensajeUseCase,
    private val updateMensajeUseCase: UpdateMensajeUseCase,
    private val deleteMensajeUseCase: DeleteMensajeUseCase
) : ViewModel() {


    private var _mensajes = getMensajeUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val mensajes: StateFlow<List<Mensaje>> = _mensajes


    fun save(mensaje: Mensaje) {
        viewModelScope.launch {
            saveMensajeUseCase(mensaje)
        }
    }

    fun update(mensaje: Mensaje) {
        viewModelScope.launch {
            updateMensajeUseCase(mensaje)
        }
    }

    fun getById(mensajeId: String): Mensaje? {
        return mensajes.value.find { it.id == mensajeId }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            deleteMensajeUseCase(id)
        }
    }



    fun compartir(mensaje: Mensaje){
        Log.d("MensajeViewModel", "Esto hará algo")
    }
}