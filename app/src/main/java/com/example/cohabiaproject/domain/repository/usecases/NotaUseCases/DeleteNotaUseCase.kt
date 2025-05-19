package com.example.cohabiaproject.domain.repository.usecases.NotaUseCases

import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.NotaRepository

class DeleteNotaUseCase(private val notaRepository: NotaRepository) {
    operator suspend fun invoke(id: String) {
        notaRepository.delete(id)
    }
}