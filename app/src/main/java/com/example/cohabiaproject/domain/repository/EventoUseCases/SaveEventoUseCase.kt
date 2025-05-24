package com.example.cohabiaproject.domain.repository.EventoUseCases

import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class SaveEventoUseCase(private val eventoRepository: EventoRepository) {
    operator suspend fun invoke(evento: Evento) {
        eventoRepository.save(evento)
    }
}