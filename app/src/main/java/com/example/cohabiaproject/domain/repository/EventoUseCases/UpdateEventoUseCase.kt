package com.example.cohabiaproject.domain.repository.EventoUseCases

import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class UpdateEventoUseCase(private val eventoRepository: EventoRepository) {
    operator suspend fun invoke(evento: Evento) {
        eventoRepository.update(evento)
    }
}