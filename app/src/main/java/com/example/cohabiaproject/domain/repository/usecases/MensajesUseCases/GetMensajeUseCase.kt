package com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases

import com.example.cohabiaproject.domain.model.Mensaje
import com.example.cohabiaproject.domain.repository.MensajeRepository
import kotlinx.coroutines.flow.Flow

class GetMensajeUseCase(private val mensajeRepository: MensajeRepository) {
    operator fun invoke(): Flow<List<Mensaje>> {
        return mensajeRepository.list()
    }
}