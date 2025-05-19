package com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import kotlinx.coroutines.flow.Flow

class GetFinanzaEsteMesUseCase(private val finanzaRepository: FinanzaRepository) {
    operator fun invoke(mes: Int, ano: Int): Flow<List<Finanza>> {
        return finanzaRepository.listEsteMes(mes = mes, ano = ano)
    }
}