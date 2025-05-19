package com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import kotlinx.coroutines.flow.Flow

class GetElectrodomesticoUseCase(private val electrodomesticoRepository: ElectrodomesticoRepository) {
    operator fun invoke(): Flow<List<Electrodomestico>> {
        return electrodomesticoRepository.list()
    }
}