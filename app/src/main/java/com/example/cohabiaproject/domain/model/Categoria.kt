package com.example.cohabiaproject.domain.model

import com.google.firebase.Timestamp

data class Categoria(
    val id: String = "",
    val nombre: String,

){
    constructor() : this("", "")
}