package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Producto
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {
    suspend fun getById(id: String): Producto?
    fun list(): Flow<List<Producto>>
    suspend fun save(producto: Producto): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun update(producto: Producto)
}
