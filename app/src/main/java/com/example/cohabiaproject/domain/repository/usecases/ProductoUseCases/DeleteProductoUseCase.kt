package com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases

import com.example.cohabiaproject.domain.repository.ProductoRepository

class DeleteProductoUseCase(private val productoRepository: ProductoRepository) {
    operator suspend fun invoke(id: String) {
        productoRepository.delete(id)
    }
}