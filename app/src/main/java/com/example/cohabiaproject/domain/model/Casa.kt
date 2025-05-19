package com.example.cohabiaproject.domain.model

data class Casa(
    val id: String = "",
    val nombre: String = "",
    val miembros: List<String> = emptyList()
)