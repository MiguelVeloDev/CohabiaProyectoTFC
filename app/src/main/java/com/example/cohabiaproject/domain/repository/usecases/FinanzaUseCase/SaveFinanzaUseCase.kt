package com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

class SaveFinanzaUseCase(private val finanzaRepository: FinanzaRepository) {
    operator suspend fun invoke(finanza: Finanza) {
        finanzaRepository.save(finanza)
    }
}