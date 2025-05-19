package com.example.cohabiaproject.domain.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object Sesion {
    var userId: String = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    var casaId: String = ""
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

                if (!casa.isEmpty) {
                    casaId = casa.documents.first().id
                } else {
                    casaId = ""
                }
            } catch (e: Exception) {
                casaId = ""
            }
        }
        Log.d("CasaSesion", "$userId $casaId")
    }

    suspend fun cerrarSesion() {
        val auth = FirebaseAuth.getInstance().signOut()
        userId = ""
        casaId = ""
    }
}

