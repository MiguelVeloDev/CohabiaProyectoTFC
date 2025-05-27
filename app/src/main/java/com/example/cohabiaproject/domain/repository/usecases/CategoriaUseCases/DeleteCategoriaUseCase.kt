package com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases

import com.example.cohabiaproject.domain.repository.CategoriaRepository
import com.example.cohabiaproject.domain.repository.ProductoRepository

class DeleteCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    operator suspend fun invoke(id: String) {
        categoriaRepository.delete(id)
    }
}