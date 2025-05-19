package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Nota
import kotlinx.coroutines.flow.Flow

interface FinanzaRepository {
    suspend fun getById(id: String): Finanza?
    fun list(): Flow<List<Finanza>>
    suspend fun save(finanza: Finanza): String
    suspend fun delete(id: String): Boolean
    suspend fun update(finanza: Finanza)
    fun listEsteMes(mes: Int, ano: Int): Flow<List<Finanza>>
    fun listTodosEsteMes(mes: Int, ano: Int): Flow<List<Finanza>>
    fun listDeudas(): Flow<List<Finanza>>
}
