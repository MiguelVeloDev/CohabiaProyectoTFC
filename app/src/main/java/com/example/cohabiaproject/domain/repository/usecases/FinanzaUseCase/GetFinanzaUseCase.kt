package com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import kotlinx.coroutines.flow.Flow

class GetFinanzaUseCase(private val finanzaRepository: FinanzaRepository) {
    operator fun invoke(): Flow<List<Finanza>> {
        return finanzaRepository.list()
    }
}