package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Evento
import kotlinx.coroutines.flow.Flow

interface EventoRepository {
    suspend fun getById(id: String): Evento?
    fun list(): Flow<List<Evento>>
    suspend fun save(nota: Evento): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(nota: Evento)
}
