package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Evento(
    val id: String = "",
    val contenido: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val tipo : String = ""
) {
    constructor() : this("", "",Timestamp.now())
}
