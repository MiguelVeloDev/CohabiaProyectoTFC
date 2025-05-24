package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Nota(
    val id: String = "",
    val titulo: String = "",
    val contenido: String = "",
    val fecha: Timestamp = Timestamp.now(),
    var fijada : Boolean = false
) {
    constructor() : this("", "", "", Timestamp.now())
}
