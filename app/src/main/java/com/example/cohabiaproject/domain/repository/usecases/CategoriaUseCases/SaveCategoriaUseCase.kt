package com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases

import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.repository.CategoriaRepository

class SaveCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    operator suspend fun invoke(categoria: Categoria) {
        categoriaRepository.save(categoria)
    }
}