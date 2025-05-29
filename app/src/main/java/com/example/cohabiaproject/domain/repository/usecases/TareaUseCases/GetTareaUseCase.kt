package com.example.cohabiaproject.domain.repository.usecases.TareaUseCases

import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.domain.repository.TareaRepository
import kotlinx.coroutines.flow.Flow

class GetTareaUseCase(private val tareaRepository: TareaRepository) {
    operator fun invoke(): Flow<List<Tarea>> {
        return tareaRepository.list()
    }
}