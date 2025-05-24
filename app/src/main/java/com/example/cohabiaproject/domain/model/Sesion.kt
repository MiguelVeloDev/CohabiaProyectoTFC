package com.example.cohabiaproject.domain.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object Sesion {
    var userId: String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    var casaId: String = ""
    var nombreUsuario: String = ""
    suspend fun cargarSesion() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        userId = user?.uid.orEmpty()

        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            try {
                // Obtener casaId
                val casa = db.collection("casas")
                    .whereArrayContains("miembros", userId)
                    .get()
                    .await()

                casaId = if (!casa.isEmpty) casa.documents.first().id else ""

                // Obtener nombre de usuario
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
        val auth = FirebaseAuth.getInstance().signOut()
        userId = ""
        casaId = ""
    }
}

