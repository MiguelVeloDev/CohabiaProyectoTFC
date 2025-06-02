package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.repository.EventoUseCases.DeleteEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.GetEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.SaveEventoUseCase
import com.example.cohabiaproject.domain.repository.EventoUseCases.UpdateEventoUseCase
import com.example.cohabiaproject.ui.theme.AzulGastos
import com.example.cohabiaproject.ui.theme.AzulTareas
import com.example.cohabiaproject.ui.theme.MoradoElectrodomesticos
import com.example.cohabiaproject.ui.theme.RojoCompras
import com.example.cohabiaproject.ui.theme.VerdeNotas
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
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


    val ultimosTresEventos: StateFlow<List<Evento>> = _eventos
        .map { it.take(4) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

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
            "COMPRA" -> "${Sesion.nombreUsuario} ha comprado ${dato} productos"
            "TAREA" -> "${Sesion.nombreUsuario} ha completado: ${dato}"
            else -> "Evento desconocido"
        }
    }




    fun compartir(evento: Evento){
        Log.d("EventoViewModel", "Esto hará algo")
    }
}