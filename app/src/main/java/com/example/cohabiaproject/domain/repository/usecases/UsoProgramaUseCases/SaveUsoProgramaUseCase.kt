package com.example.cohabiaproject.domain.repository.usecases.UsoProgramaUseCases

import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository

class SaveUsoProgramaUseCase(private val usoProgramaRepository: UsoProgramaRepository) {
    operator suspend fun invoke(usoPrograma: UsoPrograma) {
        usoProgramaRepository.save(usoPrograma)
    }
}