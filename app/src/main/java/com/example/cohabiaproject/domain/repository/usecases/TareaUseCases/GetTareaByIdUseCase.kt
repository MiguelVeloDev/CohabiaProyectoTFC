package com.example.cohabiaproject.domain.repository.usecases.TareaUseCases

import com.example.cohabiaproject.domain.repository.TareaRepository

class GetTareaByIdUseCase(private val tareaRepository: TareaRepository) {
    operator suspend fun invoke(id: String) {
        tareaRepository.getById(id)
    }
}