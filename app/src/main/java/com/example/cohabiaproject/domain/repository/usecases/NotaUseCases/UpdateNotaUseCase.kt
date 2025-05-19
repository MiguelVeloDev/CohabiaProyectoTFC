package com.example.cohabiaproject.domain.repository.usecases.NotaUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class UpdateNotaUseCase(private val notaRepository: NotaRepository) {
    operator suspend fun invoke(nota: Nota) {
        notaRepository.update(nota)
    }
}