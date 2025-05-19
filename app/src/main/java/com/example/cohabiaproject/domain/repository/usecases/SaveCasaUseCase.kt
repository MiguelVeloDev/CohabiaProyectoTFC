package com.example.cohabiaproject.domain.repository.usecases

import com.example.cohabiaproject.domain.repository.CasaRepository

class SaveCasaUseCase(private val casaRepository : CasaRepository) {
    suspend operator fun invoke(nombre : String): String {
        return casaRepository.saveCasa(nombre)
    }
}