package com.example.cohabiaproject.domain.repository.EventoUseCases

import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class GetEventoByIdUseCase(private val eventoRepository: EventoRepository) {
    operator suspend fun invoke(id: String) {
        eventoRepository.getById(id)
    }
}