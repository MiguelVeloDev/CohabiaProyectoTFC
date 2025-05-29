package com.example.cohabiaproject.presentation.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.domain.model.ProgramaElectrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.GetElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.SaveElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases.UpdateElectrodomesticoUseCase
import com.example.cohabiaproject.domain.repository.usecases.TareaUseCases.DeleteTareaUseCase
import com.example.cohabiaproject.domain.repository.usecases.TareaUseCases.GetTareaEsteMesUseCase
import com.example.cohabiaproject.domain.repository.usecases.TareaUseCases.GetTareaUseCase
import com.example.cohabiaproject.domain.repository.usecases.TareaUseCases.SaveTareaUseCase
import com.example.cohabiaproject.domain.repository.usecases.TareaUseCases.UpdateTareaUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap

class TareaViewModel(
    private val getTareaUseCase: GetTareaUseCase,
    private val saveTareaUseCase: SaveTareaUseCase,
    private val updateTareaUseCase: UpdateTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val getTareaEsteMesUseCase: GetTareaEsteMesUseCase
) : ViewModel() {


    private var _tareas = getTareaUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tareas: StateFlow<List<Tarea>> = _tareas

    /*val tareasRecurrentes: StateFlow<List<Tarea>> =
        tareas.map { lista -> lista.filter { it.recurrente } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
*/

    private var _tareasEsteMes = getTareaEsteMesUseCase(
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1,
        ano = Calendar.getInstance().get(Calendar.YEAR)
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val tareasEsteMes: StateFlow<List<Tarea>> = _tareasEsteMes

    @RequiresApi(Build.VERSION_CODES.O)
    val tareasHoy: StateFlow<List<Tarea>> =
        _tareasEsteMes
            .map { lista -> lista.filter { generarTextoFecha(it).first == "Hoy" } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tareasRecurrentes: StateFlow<List<Tarea>> =
        _tareas
            .map { lista -> lista.filter { it.recurrente } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun save(tarea: Tarea) {
        viewModelScope.launch {
            saveTareaUseCase(tarea)
        }
    }

    fun update(tarea: Tarea) {
        viewModelScope.launch {
            updateTareaUseCase(tarea)
        }
    }


    fun borrar(id: String) {
        viewModelScope.launch {
            deleteTareaUseCase(id)
        }
    }



        @RequiresApi(Build.VERSION_CODES.O)
        fun generarTextoFecha(tarea: Tarea): Pair<String, Boolean> {
            val tareaFecha = LocalDate.of(tarea.año, tarea.mes, tarea.dia.toInt())
            val hoy = LocalDate.now()
            val ayer = hoy.minusDays(1)

            val texto = when {
                tareaFecha.isAfter(hoy) -> {
                    tareaFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }

                tareaFecha.isEqual(hoy) -> {
                    "Hoy"
                }

                tareaFecha.isEqual(ayer) -> {
                    "Ayer"
                }

                else -> {
                    tareaFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }
            }

            val esPasadoOIgualHoy = tareaFecha.isBefore(hoy)

            return Pair(texto, esPasadoOIgualHoy)
        }

        fun compartir(tarea: Tarea) {
            Log.d("TareaViewModel", "Esto hará algo")
        }
    }
