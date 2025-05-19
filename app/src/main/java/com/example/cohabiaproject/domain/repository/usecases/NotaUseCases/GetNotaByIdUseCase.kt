package com.example.cohabiaproject.domain.repository.usecases.NotaUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class GetNotaByIdUseCase(private val notaRepository: NotaRepository) {
    operator suspend fun invoke(id: String) {
        notaRepository.getById(id)
    }
}