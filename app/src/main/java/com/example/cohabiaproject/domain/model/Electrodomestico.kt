package com.example.cohabiaproject.domain.model


data class Electrodomestico(
    val id: String = "",
    val nombre: String = "",
    val tipo: String = "",
    val programas: List<ProgramaElectrodomestico> = emptyList(),
    var isRunning: Boolean = false,
    var usoProgramaActual: UsoPrograma? = null

) {
    constructor() : this(id = "", nombre = "", tipo = "", programas = emptyList(), isRunning = false)
}