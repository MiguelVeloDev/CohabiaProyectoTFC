package com.example.cohabiaproject.domain.repository.usecases.NotaUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class SaveNotaUseCase(private val notaRepository: NotaRepository) {
    operator suspend fun invoke(nota: Nota) {
        notaRepository.save(nota)
    }
}