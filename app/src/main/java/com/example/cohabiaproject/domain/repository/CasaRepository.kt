package com.example.cohabiaproject.domain.repository

import com.example.cohabiaproject.domain.model.Casa

interface CasaRepository {
        suspend fun getCasaDelUsuario(): Casa?
        suspend fun saveCasa(nombre: String): String
        suspend fun getById(casaId: String): Casa?
        suspend fun updateCasa(casa: Casa)
        suspend fun deleteCasa(casaId: String)
        suspend fun unirmeCasa(string: String)
}