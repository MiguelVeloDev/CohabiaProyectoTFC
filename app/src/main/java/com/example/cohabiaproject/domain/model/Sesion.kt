package com.example.cohabiaproject.domain.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object Sesion {
    var userId by mutableStateOf(FirebaseAuth.getInstance().currentUser?.uid.orEmpty())
    var casaId by mutableStateOf("")
    var nombreUsuario by mutableStateOf("")

    suspend fun cargarSesion() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        userId = user?.uid.orEmpty()

        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            try {
                val casa = db.collection("casas")
                    .whereArrayContains("miembros", userId)
                    .get()
                    .await()

                casaId = if (!casa.isEmpty) casa.documents.first().id else ""

                val userDoc = db.collection("usuarios")
                    .document(userId)
                    .get()
                    .await()

                nombreUsuario = userDoc.getString("nombre") ?: ""

            } catch (e: Exception) {
                casaId = ""
                nombreUsuario = ""
                Log.e("Sesion", "Error al cargar la sesión: ${e.message}")
            }
        }

        Log.d("CasaSesion", "$userId $casaId $nombreUsuario")
    }

    suspend fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()
        userId = ""
        casaId = ""
        nombreUsuario = ""
    }
}
