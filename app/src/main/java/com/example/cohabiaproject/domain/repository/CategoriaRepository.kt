package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Categoria
import kotlinx.coroutines.flow.Flow

interface CategoriaRepository {
    suspend fun getById(id: String): Categoria?
    fun list(): Flow<List<Categoria>>
    suspend fun save(producto: Categoria): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(producto: Categoria)
}
