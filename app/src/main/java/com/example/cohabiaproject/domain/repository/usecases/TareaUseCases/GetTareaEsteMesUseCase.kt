package com.example.cohabiaproject.domain.repository.usecases.TareaUseCases

import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import com.example.cohabiaproject.domain.repository.TareaRepository
import kotlinx.coroutines.flow.Flow

class GetTareaEsteMesUseCase(private val tareaRepository: TareaRepository) {
    operator fun invoke(mes: Int, ano: Int): Flow<List<Tarea>> {
        return tareaRepository.listEsteMes(mes = mes, ano = ano)
    }
}