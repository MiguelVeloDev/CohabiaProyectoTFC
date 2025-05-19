package com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases

import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.NotaRepository
import com.example.cohabiaproject.domain.repository.UsuarioRepository

class DeleteUsuarioUseCase(private val usuarioRepository: UsuarioRepository) {
    operator suspend fun invoke(usuario: Usuario) {
        usuarioRepository.delete(usuario)
    }
}