package com.example.cohabiaproject.domain.repository.usecases.UsoProgramaUseCases

import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import kotlinx.coroutines.flow.Flow

class GetUsoProgramaUseCase(private val usoProgramaRepository: UsoProgramaRepository) {
    operator fun invoke(): Flow<List<UsoPrograma>> {
        return usoProgramaRepository.list()
    }
}