package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Mensaje
import kotlinx.coroutines.flow.Flow

interface MensajeRepository {
    suspend fun getById(id: String): Mensaje?
    fun list(): Flow<List<Mensaje>>
    suspend fun save(mensaje: Mensaje): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(mensaje: Mensaje)
}
