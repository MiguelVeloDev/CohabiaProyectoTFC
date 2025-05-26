package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.repository.EventoUseCases.DeleteEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.GetEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.SaveEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.UpdateEventoUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventoViewModel(
    private val getEventoUseCase: GetEventoUseCase,
    private val saveEventoUseCase: SaveEventoUseCase,
    private val updateEventoUseCase: UpdateEventoUseCase,
    private val deleteEventoUseCase: DeleteEventoUseCase
) : ViewModel() {


    private var _eventos = getEventoUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val eventos: StateFlow<List<Evento>> = _eventos


    fun save(evento: Evento) {
        viewModelScope.launch {
            saveEventoUseCase(evento)
        }
    }

    fun update(evento: Evento) {
        viewModelScope.launch {
            updateEventoUseCase(evento)
        }
    }

    fun getById(eventoId: String): Evento? {
        return eventos.value.find { it.id == eventoId }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            deleteEventoUseCase(id)
        }
    }

    fun generarMensaje(tipo: String, dato: String): String {
        return when (tipo) {
            "NOTA" -> "${Sesion.nombreUsuario} añadió la nota \"${dato}\""
            "GASTO" -> "${Sesion.nombreUsuario} añadio un gasto de ${dato} €"
            "ELECTRODOMESTICO" -> "${Sesion.nombreUsuario} ha iniciado: ${dato}"
            else -> "Evento desconocido"
        }
    }


    fun compartir(evento: Evento){
        Log.d("EventoViewModel", "Esto hará algo")
    }
}