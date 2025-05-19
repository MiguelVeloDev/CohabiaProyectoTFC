package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.DeleteNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.GetNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.SaveNotaUseCase
import com.example.cohabiaproject.domain.repository.usecases.NotaUseCases.UpdateNotaUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class NotaViewModel(
    private val getNotaUseCase: GetNotaUseCase,
    private val saveNotaUseCase: SaveNotaUseCase,
    private val updateNotaUseCase: UpdateNotaUseCase,
    private val deleteNotaUseCase: DeleteNotaUseCase
) : ViewModel() {


    private var _notas = getNotaUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val notas: StateFlow<List<Nota>> = _notas


    fun save(nota: Nota) {
        viewModelScope.launch {
            saveNotaUseCase(nota)
        }
    }

    fun update(nota: Nota) {
        viewModelScope.launch {
            updateNotaUseCase(nota)
        }
    }

    fun getById(notaId: String): Nota? {
        return notas.value.find { it.id == notaId }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            deleteNotaUseCase(id)
        }
    }

    fun compartir(nota: Nota){
        Log.d("NotaViewModel", "Esto hará algo")
    }
}