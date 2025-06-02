package com.example.cohabiaproject.domain.repository.usecases.MensajesUseCases

import com.example.cohabiaproject.domain.model.Mensaje
import com.example.cohabiaproject.domain.repository.MensajeRepository

class SaveMensajeUseCase(private val mensajeRepository: MensajeRepository) {
    operator suspend fun invoke(mensaje: Mensaje) {
        mensajeRepository.save(mensaje)
    }
}