package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.model.Usuario
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.example.cohabiaproject.domain.repository.UsuarioRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UsuarioFirestoreRepository(val firestore: FirebaseFirestore) : UsuarioRepository {

    private val usuarioCollection = firestore.collection("usuarios")

    override suspend fun getById(id: String): Usuario? {
        return try {
            val documentSnapshot = usuarioCollection.document(id).get().await()
            documentSnapshot.toObject(Usuario::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Usuario", e)
            null
        }
    }

    override fun list(): Flow<List<Usuario>> {
        return callbackFlow {
            val listener = usuarioCollection
                .whereEqualTo("casa", Sesion.casaId)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val uso = doc.toObject(Usuario::class.java)
                        uso?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(usuario: Usuario): Boolean {
        return try {
            usuarioCollection.document(usuario.id).set(usuario).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar el Usuario", e)
            false
        }
    }

    override suspend fun delete(usuario: Usuario): Boolean {
        return try {
            usuarioCollection.document(usuario.id).delete().await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar el Usuario", e)
            false
        }
    }

    override suspend fun update(usuario: Usuario) {
        try {
            usuarioCollection
                .document(usuario.id)
                .set(usuario)
                .await()

            Log.d("Actualizacion", "Usuario actualizado correctamente")
        } catch (e: Exception) {
            Log.e("Actualizacion", "Error al actualizar el UsoPrograma", e)
        }
    }
}
