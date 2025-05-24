package com.example.cohabiaproject.domain.repository.usecases.ProductoUseCases

import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow

class GetProductoUseCase(private val productoRepository: ProductoRepository) {
    operator fun invoke(): Flow<List<Producto>> {
        return productoRepository.list()
    }
}