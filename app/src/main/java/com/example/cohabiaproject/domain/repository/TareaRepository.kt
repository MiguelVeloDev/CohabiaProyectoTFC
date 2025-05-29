package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Tarea
import kotlinx.coroutines.flow.Flow

interface TareaRepository {
    suspend fun getById(id: String): Tarea?
    fun list(): Flow<List<Tarea>>
    suspend fun save(tarea: Tarea): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(tarea: Tarea)
    fun listEsteMes(mes: Int, ano: Int): Flow<List<Tarea>>
}
