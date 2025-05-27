package com.example.cohabiaproject.domain.repository.usecases.CategoriaUseCases

import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.repository.CategoriaRepository
import com.example.cohabiaproject.domain.repository.ProductoRepository

class SaveCategoriaUseCase(private val categoriaRepository: CategoriaRepository) {
    operator suspend fun invoke(categoria: Categoria) {
        categoriaRepository.save(categoria)
    }
}