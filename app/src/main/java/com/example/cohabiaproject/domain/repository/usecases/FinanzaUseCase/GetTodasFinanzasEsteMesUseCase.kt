package com.example.cohabiaproject.domain.repository.usecases.FinanzaUseCase

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import com.example.cohabiaproject.domain.repository.NotaRepository
import kotlinx.coroutines.flow.Flow

class GetTodasFinanzasEsteMesUseCase(private val finanzaRepository: FinanzaRepository) {
    operator fun invoke(mes: Int, ano: Int): Flow<List<Finanza>> {
        return finanzaRepository.listTodosEsteMes(mes = mes, ano = ano)
    }
}