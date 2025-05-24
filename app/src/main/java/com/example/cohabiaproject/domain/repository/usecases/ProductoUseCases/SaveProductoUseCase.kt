package com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases

import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.repository.ProductoRepository

class SaveProductoUseCase(private val productoRepository: ProductoRepository) {
    operator suspend fun invoke(producto: Producto) {
        productoRepository.save(producto)
    }
}