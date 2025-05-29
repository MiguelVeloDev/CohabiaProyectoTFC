package com.example.cohabiaproject.domain.repository.usecases.TareaUseCases

import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.domain.repository.TareaRepository

class UpdateTareaUseCase(private val tareaRepository: TareaRepository) {
    operator suspend fun invoke(tarea: Tarea) {
        tareaRepository.update(tarea)
    }
}