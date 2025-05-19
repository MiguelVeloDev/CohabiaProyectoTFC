package com.example.cohabiaproject.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class ElectrodomesticoViewModel(
    private val getElectrodomesticoUseCase: GetElectrodomesticoUseCase,
    private val saveElectrodomesticoUseCase: SaveElectrodomesticoUseCase,
    private val updateElectrodomesticoUseCase: UpdateElectrodomesticoUseCase,
    private val usoProgramaRepository: UsoProgramaRepository
) : ViewModel() {

    private var _electrodomesticos = getElectrodomesticoUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val electrodomesticos: StateFlow<List<Electrodomestico>> = _electrodomesticos


    val electrodomesticosEnEjecucion: StateFlow<List<Electrodomestico>> =
        electrodomesticos
            .map { list -> list.filter { it.isRunning } }
            .stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = emptyList()
            )

    private val _tiemposRestantes = ConcurrentHashMap<String, MutableStateFlow<Long>>()

    fun tiempoRestante(electrodomesticoId: String): StateFlow<Long> {
        return _tiemposRestantes.getOrPut(electrodomesticoId) { MutableStateFlow(0L) }
    }

    fun save(electrodomestico: Electrodomestico) {
        viewModelScope.launch {
            saveElectrodomesticoUseCase(electrodomestico)
        }
    }

    fun update(electrodomestico: Electrodomestico) {
        viewModelScope.launch {
            updateElectrodomesticoUseCase(electrodomestico)
        }
    }

    fun saveUsoPrograma(usoPrograma: UsoPrograma) {
        viewModelScope.launch {
            usoProgramaRepository.save(usoPrograma)
        }
    }

    fun iniciarContador(electrodomestico: Electrodomestico) {
        val electrodomesticoId = electrodomestico.id
        viewModelScope.launch {
            while (electrodomestico.isRunning && electrodomestico.usoProgramaActual != null) {
                val tiempo = calcularTiempoRestante(electrodomestico)
                _tiemposRestantes.getOrPut(electrodomesticoId) { MutableStateFlow(0L) }.value = tiempo
                if (tiempo <= 0) {
                    finalizarPrograma(electrodomestico)
                    break
                }
                delay(1000)
            }
        }
    }


    fun calcularTiempoRestante(electrodomestico: Electrodomestico): Long {
        val uso = electrodomestico.usoProgramaActual ?: return 0L
        val programa = electrodomestico.programas.find { it.nombre == uso.programaId } ?: return 0L

        val duracionTotal = programa.minutos * 60 * 1000L

        val tiempoTranscurrido = if (uso.pausado) {
            uso.timestampUltimaPausa?.minus(uso.inicio) ?: 0L
        } else {
            System.currentTimeMillis() - uso.inicio
        }

        val tiempoReal = tiempoTranscurrido - uso.tiempoPausado
        return (duracionTotal - tiempoReal).coerceAtLeast(0L)
    }



    fun finalizarPrograma(electrodomestico: Electrodomestico) {
        electrodomestico.usoProgramaActual = null
        update(electrodomestico)
        _tiemposRestantes.remove(electrodomestico.id)
    }


    fun resetContador(electrodomestico: Electrodomestico) {
        _tiemposRestantes.remove(electrodomestico.id)
    }


}