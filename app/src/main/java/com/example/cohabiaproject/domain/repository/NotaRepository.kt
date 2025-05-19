package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import kotlinx.coroutines.flow.Flow

interface NotaRepository {
    suspend fun getById(id: String): Nota?
    fun list(): Flow<List<Nota>>
    suspend fun save(nota: Nota): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(nota: Nota)
}
