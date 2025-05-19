package com.example.cohabiaproject.domain.model

data class UsoPrograma(
    val id: String = "",
    val programaId: String = "",
    val electrodomesticoId: String = "",
    val inicio: Long = 0L,
    val estado: String = "en_ejecucion",
    var pausado: Boolean = false,
    var tiempoPausado: Long = 0L,
    var timestampUltimaPausa: Long? = null
){
    constructor() : this("", "", "", 0L, "")

}