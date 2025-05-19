package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Nota
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.NotaRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class NotaFirestoreRepository(val firestore: FirebaseFirestore):
    NotaRepository {

    private fun notasCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("notas")

    override suspend fun getById(id: String): Nota? {
        return try {
            val documentSnapshot = notasCollection().document(id).get().await()
            documentSnapshot.toObject(Nota::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Nota>> {
        return callbackFlow {
            val listener = notasCollection()
                .orderBy("titulo", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val nota = doc.toObject(Nota::class.java)
                        nota?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(nota: Nota): Boolean {
        return try {
            notasCollection().add(nota).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            notasCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(nota: Nota) {
        try {
            notasCollection()
                .document(nota.id)
                .set(nota)
                .await()

            Log.d(TAG, "Nota actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}