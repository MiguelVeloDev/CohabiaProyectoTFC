package com.example.cohabiaproject.domain.repository.EventoUseCases

import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.EventoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository
import kotlinx.coroutines.flow.Flow

class GetEventoUseCase(private val eventoRepository: EventoRepository) {
    operator fun invoke(): Flow<List<Evento>> {
        return eventoRepository.list()
    }
}