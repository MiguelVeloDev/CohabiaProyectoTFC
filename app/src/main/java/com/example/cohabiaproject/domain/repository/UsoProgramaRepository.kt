package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.UsoPrograma
import kotlinx.coroutines.flow.Flow

interface UsoProgramaRepository {
    suspend fun getById(id: String): UsoPrograma?
    fun list(): Flow<List<UsoPrograma>>
    suspend fun save(usoPrograma: UsoPrograma): Boolean
    suspend fun delete(usoPrograma: UsoPrograma): Boolean
    suspend fun update(usoPrograma: UsoPrograma)
}
