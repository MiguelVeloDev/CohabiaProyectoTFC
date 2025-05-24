package com.example.cohabiaproject.domain.repository.EventoUseCases

import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class DeleteEventoUseCase(private val eventoRepository: EventoRepository) {
    operator suspend fun invoke(id: String) {
        eventoRepository.delete(id)
    }
}