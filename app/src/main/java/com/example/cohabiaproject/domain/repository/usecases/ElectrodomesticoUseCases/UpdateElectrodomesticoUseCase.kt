package com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository

class UpdateElectrodomesticoUseCase(private val electrodomesticoRepository: ElectrodomesticoRepository) {
    operator suspend fun invoke(electrodomestico: Electrodomestico) {
        electrodomesticoRepository.update(electrodomestico)
    }
}