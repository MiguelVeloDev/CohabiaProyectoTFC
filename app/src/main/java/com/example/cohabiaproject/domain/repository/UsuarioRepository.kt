package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    suspend fun getById(id: String): Usuario?
    fun list(): Flow<List<Usuario>>
    suspend fun save(usuario: Usuario): Boolean
    suspend fun delete(usuario: Usuario): Boolean
    suspend fun update(usuario: Usuario)

}
