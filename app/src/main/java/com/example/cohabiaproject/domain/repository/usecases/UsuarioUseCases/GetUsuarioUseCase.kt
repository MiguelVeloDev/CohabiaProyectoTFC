package com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases

import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow

class GetUsuarioUseCase(private val usuarioRepository: UsuarioRepository) {
    operator fun invoke(): Flow<List<Usuario>> {
        return usuarioRepository.list()
    }
}