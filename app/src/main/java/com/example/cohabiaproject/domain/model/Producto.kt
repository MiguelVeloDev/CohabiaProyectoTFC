package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Producto(
    val id: String = "",
    val nombre: String,
    val categoria: String,
    val recurrente: Boolean = false,
    var comprado : Boolean = false,
){
constructor() : this("", "","")
}