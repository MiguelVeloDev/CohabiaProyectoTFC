package com.example.cohabiaproject.presentation.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.DeleteFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetFinanzaEsteMesUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetTodasDeudasUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.GetTodasFinanzasEsteMesUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.SaveFinanzaUseCase
import com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase.UpdateFinanzaUseCase
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class FinanzasViewModel(
    private val getFinanzaUseCase: GetFinanzaUseCase,
    private val saveFinanzaUseCase: SaveFinanzaUseCase,
    private val updateFinanzaUseCase: UpdateFinanzaUseCase,
    private val deleteFinanzaUseCase: DeleteFinanzaUseCase,
    private val getFinanzaEsteMesUseCase: GetFinanzaEsteMesUseCase,
    private val getTodasFinanzasEsteMesUseCase: GetTodasFinanzasEsteMesUseCase,
    private val getTodasDeudasUseCase: GetTodasDeudasUseCase
) : ViewModel() {

    private var _finanzas = getFinanzaUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val finanzas: StateFlow<List<Finanza>> = _finanzas


    private var _todosGastosEsteMes = getTodasFinanzasEsteMesUseCase(
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1,
        ano = Calendar.getInstance().get(Calendar.YEAR)
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val todosGastosEsteMes: StateFlow<List<Finanza>> = _todosGastosEsteMes

    private var _gastosEsteMes = getFinanzaEsteMesUseCase(
        mes = Calendar.getInstance().get(Calendar.MONTH) + 1,
        ano = Calendar.getInstance().get(Calendar.YEAR)
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val gastosEsteMes: StateFlow<List<Finanza>> = _gastosEsteMes


    private var _deudas = getTodasDeudasUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val deudas: StateFlow<List<Finanza>> = _deudas


    fun save(finanza: Finanza) {
        viewModelScope.launch {
            saveFinanzaUseCase(finanza)
        }
    }

    fun update(finanza: Finanza) {
        viewModelScope.launch {
            updateFinanzaUseCase(finanza)
        }
    }

    fun getGastoById(finanzaId: String): Finanza? {
        return finanzas.value.find { it.id == finanzaId }
    }

    fun borrar(id: String) {
        viewModelScope.launch {
            deleteFinanzaUseCase(id)
        }
    }

    fun compartir(finanza: Finanza) {
        Log.d("FinanzaViewModel", "Finanza a compartir: $finanza")
    }

    fun crearFinanza(
        concepto: String,
        cantidad: Double,
        usuarioPaga: List<String>,
        usuariosParticipan: List<String>,
        usuariosDeuda: List<String>
    ): Finanza {

        val now = Calendar.getInstance()
        return Finanza(
            concepto = concepto,
            cantidad = cantidad,
            fecha = Timestamp.now(),
            dia = now.get(Calendar.DAY_OF_MONTH),
            mes = now.get(Calendar.MONTH) + 1,
            año = now.get(Calendar.YEAR),
            usuariosParticipan = usuariosParticipan,
            usuarioPaga = usuarioPaga,
            usuariosDeuda = usuariosDeuda,
            hayDeuda = usuariosDeuda.isNotEmpty()
        )
    }

    fun convertirNombreAId(listaNombres: List<String>, listaUsuarios: List<Usuario>): List<String> {
        var listaDeIds = mutableListOf<String>()
        for (nombre in listaNombres) {
            for (usuario in listaUsuarios) {
                if (usuario.nombre == nombre) {
                    listaDeIds.add(usuario.id)
                }
            }
        }
        return listaDeIds
    }

    fun cargarDeudasConmigo(usuarios: List<Usuario>): Map<String, Double> {
        var mapDeudas = mutableMapOf<String, Double>()
        for (usuario in usuarios.filter { it.id != Sesion.userId }) {
            var cantidadDeuda = 0.0
            for (deuda in deudas.value) {
                if (deuda.usuariosDeuda.contains(Sesion.userId) && deuda.usuarioPaga.contains(
                        usuario.id
                    )
                ) {
                    cantidadDeuda += deuda.cantidad/ deuda.usuariosParticipan.size
                }
                mapDeudas[usuario.id] = cantidadDeuda
            }

        }
        return mapDeudas
    }

    fun cargarMisDeudas(usuarios: List<Usuario>): Map<String, Double> {
        var mapDeudas = mutableMapOf<String, Double>()
        for (usuario in usuarios.filter { it.id != Sesion.userId }) {
            var cantidadDeuda = 0.0
            for (deuda in deudas.value) {
                if (deuda.usuariosDeuda.contains(usuario.id) && deuda.usuarioPaga.contains(Sesion.userId)) {
                    cantidadDeuda += deuda.cantidad
                }
                mapDeudas[usuario.id] = cantidadDeuda/ deuda.usuariosParticipan.size
            }

        }
        return mapDeudas
    }


    fun calcularDeudaTotal(usuarios: List<Usuario>): Map<String, Double> {
        var mapDeudasTotal = mutableMapOf<String, Double>()
        var mapMisDeudas = cargarMisDeudas(usuarios)
        var mapDeudasConmigo = cargarDeudasConmigo(usuarios)

        mapMisDeudas.entries.zip(mapDeudasConmigo.values) { entrada, deudaConmigo ->
            val idUsuario = entrada.key
            val deudaQueYoTengo = entrada.value

            val saldo = deudaConmigo - deudaQueYoTengo

            mapDeudasTotal[idUsuario] = saldo

        }
        return mapDeudasTotal
    }
}