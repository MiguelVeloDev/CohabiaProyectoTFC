package com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases

import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.repository.ProductoRepository

class UpdateProductoUseCase(private val productoRepository: ProductoRepository) {
    operator suspend fun invoke(producto: Producto) {
        productoRepository.update(producto)
    }
}