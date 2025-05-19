package com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase

import com.example.cohabiaproject.domain.repository.FinanzaRepository

class GetGastoByIdUseCase(private val finanzaRepository: FinanzaRepository) {
    operator suspend fun invoke(id: String) {
        finanzaRepository.getById(id)
    }
}