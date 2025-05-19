package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Finanza(
    val id: String = "",
    var concepto: String = "",
    var cantidad: Double = 0.0,
    var fecha: Timestamp = Timestamp.now(),
    var usuarioPaga : List<String> = emptyList(),
    var usuariosParticipan : List<String> = emptyList(),
    val dia: Int = 0,
    val mes: Int = 0,
    val año: Int = 0,
    var usuariosDeuda : List<String> = emptyList(),
    var hayDeuda : Boolean = false
) {
    constructor() : this("", "",0.0, Timestamp.now())
}
