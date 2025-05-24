package com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases

import com.example.cohabiaproject.domain.repository.ProductoRepository

class GetProductoByIdUseCase(private val productoRepository: ProductoRepository) {
    operator suspend fun invoke(id: String) {
        productoRepository.getById(id)
    }
}