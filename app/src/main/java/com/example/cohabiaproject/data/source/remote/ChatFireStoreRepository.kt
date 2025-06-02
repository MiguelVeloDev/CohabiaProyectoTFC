package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Mensaje
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.MensajeRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MensajeFirestoreRepository(val firestore: FirebaseFirestore):
    MensajeRepository {

    private fun mensajesCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("mensajes")

    override suspend fun getById(id: String): Mensaje? {
        return try {
            val documentSnapshot = mensajesCollection().document(id).get().await()
            documentSnapshot.toObject(Mensaje::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Mensaje>> {
        return callbackFlow {
            val listener = mensajesCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val mensaje = doc.toObject(Mensaje::class.java)
                        mensaje?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(mensaje: Mensaje): Boolean {
        return try {
            mensajesCollection().add(mensaje).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            mensajesCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(mensaje: Mensaje) {
        try {
            mensajesCollection()
                .document(mensaje.id)
                .set(mensaje)
                .await()

            Log.d(TAG, "Mensaje actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}