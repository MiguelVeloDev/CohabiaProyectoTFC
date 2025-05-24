package com.example.cohabiaproject.domain.repository.usecases.ElectrodomesticoUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.FinanzaRepository

class GetElectrodomesticoByIdUseCase(private val electrodomesticoRepository: ElectrodomesticoRepository) {
    operator suspend fun invoke(id: String) : Electrodomestico? {
        return electrodomesticoRepository.getById(id)
    }
}