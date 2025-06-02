package com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases

import com.example.cohabiaproject.domain.repository.MensajeRepository

class DeleteMensajeUseCase(private val mensajeRepository: MensajeRepository) {
    operator suspend fun invoke(id: String) {
        mensajeRepository.delete(id)
    }
}