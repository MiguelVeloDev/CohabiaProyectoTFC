package com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases

import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.UsuarioRepository

class SaveUsuarioUseCase(private val usuarioRepository: UsuarioRepository) {
    operator suspend fun invoke(usuario: Usuario) {
        usuarioRepository.save(usuario)
    }
}