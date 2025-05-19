package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Electrodomestico
import kotlinx.coroutines.flow.Flow

interface ElectrodomesticoRepository {
    suspend fun getById(id: String): Electrodomestico?
    fun list(): Flow<List<Electrodomestico>>
    suspend fun save(electrodomestico: Electrodomestico) : Boolean
    suspend fun delete(electrodomestico: Electrodomestico) : Boolean
    suspend fun update(electrodomestico: Electrodomestico)
}