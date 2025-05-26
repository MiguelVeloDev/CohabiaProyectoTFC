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

    val deudaTexto = "Pago de deuda"


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

    fun calcularDeudaTotal(usuarios: List<Usuario>): Map<String, Double> {
        val mapDeudasTotal = mutableMapOf<String, Double>()

        for (usuario in usuarios.filter { it.id != Sesion.userId }) {
            val listaDeudas = generarListaDeudasUsuario(usuario.id)
            var saldo = 0.0

            for ((esDeudaDelUsuario, deuda) in listaDeudas) {
                saldo += if (esDeudaDelUsuario) -deuda.cantidad else deuda.cantidad
            }

            mapDeudasTotal[usuario.id] = saldo
        }

        return mapDeudasTotal
    }


    fun generarListaDeudasUsuario (usuarioId: String):List<Pair<Boolean, Finanza>> {
        val listaDeudas = mutableListOf<Pair<Boolean, Finanza>>()
        for (deuda in deudas.value){
            if (deuda.usuarioPaga.contains(Sesion.userId) && deuda.usuariosDeuda.contains(usuarioId)){
                listaDeudas.add(Pair(false, deuda.copy(cantidad = deuda.cantidad / deuda.usuariosParticipan.size)))
            }else if (deuda.usuarioPaga.contains(usuarioId) && deuda.usuariosDeuda.contains(Sesion.userId)){
                listaDeudas.add(Pair(true, deuda.copy(cantidad = deuda.cantidad / deuda.usuariosParticipan.size)))
            }
    }
        return listaDeudas
}
    fun numeroAMes(numero: Int): String {
        return when (numero) {
            1 -> "ENE"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "ABR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AGO"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DIC"
            else -> ""
        }
    }


}