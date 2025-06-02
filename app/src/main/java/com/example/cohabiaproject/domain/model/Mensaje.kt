package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Mensaje(
    val id: String = "",
    val contenido: String = "",
    val fecha: Timestamp = Timestamp.now(),
    val usuario: String = ""
){
constructor() : this("", "", Timestamp.now(), "")
}