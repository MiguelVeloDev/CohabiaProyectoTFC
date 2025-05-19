package com.example.cohabiaproject.domain.repository.usecases.NotaUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository
import kotlinx.coroutines.flow.Flow

class GetNotaUseCase(private val notaRepository: NotaRepository) {
    operator fun invoke(): Flow<List<Nota>> {
        return notaRepository.list()
    }
}