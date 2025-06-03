package com.example.cohabiaproject.domain.model

data class Tarea(
    val id: String = "",
    val contenido: String = "",
    val usuario: String = "",
    val dia: String? = "",
    val mes: Int? = 0,
    val año: Int? = 0,
    val hora: String? = "",
    val recurrente: Boolean = false
) {
    constructor() : this("", "", "", "", 0, 0, "")
}
