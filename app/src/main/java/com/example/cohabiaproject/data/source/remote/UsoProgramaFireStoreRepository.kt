package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.UsoProgramaRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UsoProgramaFirestoreRepository(val firestore: FirebaseFirestore) : UsoProgramaRepository {

    private val usosCollection = firestore.collection("usos_programa")

    override suspend fun getById(id: String): UsoPrograma? {
        return try {
            val documentSnapshot = usosCollection.document(id).get().await()
            documentSnapshot.toObject(UsoPrograma::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el UsoPrograma", e)
            null
        }
    }

    override fun list(): Flow<List<UsoPrograma>> {
        return callbackFlow {
            val listener = usosCollection
                .orderBy("fechaInicio", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val uso = doc.toObject(UsoPrograma::class.java)
                        uso?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(usoPrograma: UsoPrograma): Boolean {
        return try {
            usosCollection.add(usoPrograma).await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar el UsoPrograma", e)
            false
        }
    }

    override suspend fun delete(usoPrograma: UsoPrograma): Boolean {
        return try {
            usosCollection.document(usoPrograma.id).delete().await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar el UsoPrograma", e)
            false
        }
    }

    override suspend fun update(usoPrograma: UsoPrograma) {
        try {
            usosCollection
                .document(usoPrograma.id)
                .set(usoPrograma)
                .await()

            Log.d(TAG, "UsoPrograma actualizado correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el UsoPrograma", e)
        }
    }
}
