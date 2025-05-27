package com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases

import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.repository.CategoriaRepository
import kotlinx.coroutines.flow.Flow

class GetCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    operator fun invoke(): Flow<List<Categoria>> {
        return categoriaRepository.list()
    }
}