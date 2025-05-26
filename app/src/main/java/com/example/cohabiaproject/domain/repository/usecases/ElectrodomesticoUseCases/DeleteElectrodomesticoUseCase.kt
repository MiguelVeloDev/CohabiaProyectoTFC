package com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases

import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.ProductoRepository

class DeleteElectrodomesticoUseCase(private val electrodomesticoRepository: ElectrodomesticoRepository) {
    operator suspend fun invoke(id: String) {
        electrodomesticoRepository.delete(id)
    }
}