package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Evento
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.EventoRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class EventoFirestoreRepository(val firestore: FirebaseFirestore):
    EventoRepository {

    private fun eventosCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("eventos")

    override suspend fun getById(id: String): Evento? {
        return try {
            val documentSnapshot = eventosCollection().document(id).get().await()
            documentSnapshot.toObject(Evento::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Evento>> {
        return callbackFlow {
            val listener = eventosCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val evento = doc.toObject(Evento::class.java)
                        evento?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(evento: Evento): Boolean {
        return try {
            eventosCollection().add(evento).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            eventosCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(evento: Evento) {
        try {
            eventosCollection()
                .document(evento.id)
                .set(evento)
                .await()

            Log.d(TAG, "Evento actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}