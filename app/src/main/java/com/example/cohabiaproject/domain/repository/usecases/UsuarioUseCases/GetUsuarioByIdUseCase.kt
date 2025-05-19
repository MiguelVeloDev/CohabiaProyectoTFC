package com.example.cohabiaproject.domain.repository.usecases.UsuarioUseCases

import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository
import com.example.cohabiaproject.domain.repository.UsuarioRepository

class GetUsuarioByIdUseCase(private val usuarioRepository: UsuarioRepository) {
    operator suspend fun invoke(id: String): Usuario? {
        return usuarioRepository.getById(id)
    }
}