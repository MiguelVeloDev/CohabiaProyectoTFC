package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.repository.FinanzaRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class FinanzaFireStoreRepository(val firestore: FirebaseFirestore) : FinanzaRepository {

    private fun gastosCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("gastos")

    override suspend fun getById(id: String): Finanza? {
        return try {
            val documentSnapshot =  gastosCollection().document(id).get().await()
            documentSnapshot.toObject(Finanza::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener la Finanza", e)
            null
        }
    }

    override fun list(): Flow<List<Finanza>> {
        return callbackFlow {
            val listener =  gastosCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val finanza = doc.toObject(Finanza::class.java)
                        finanza?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override fun listEsteMes(mes: Int, año: Int): Flow<List<Finanza>> {
        return callbackFlow {
            val listener =  gastosCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .whereEqualTo("mes", mes)
                .whereEqualTo("año", año)
                .whereArrayContains("usuarioPaga", Sesion.userId)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val finanza = doc.toObject(Finanza::class.java)
                        finanza?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override fun listTodosEsteMes(mes: Int, año: Int): Flow<List<Finanza>> {
        return callbackFlow {
            val listener =  gastosCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .whereEqualTo("mes", mes)
                .whereEqualTo("año", año)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val finanza = doc.toObject(Finanza::class.java)
                        finanza?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override fun listDeudas(): Flow<List<Finanza>> {
        return callbackFlow {
            val listener =  gastosCollection()
                .orderBy("fecha", Query.Direction.DESCENDING)
                .whereEqualTo("hayDeuda", true)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val finanza = doc.toObject(Finanza::class.java)
                        finanza?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }



    override suspend fun save(finanza: Finanza): String {
        return try {
            val documentReference =  gastosCollection().add(finanza).await()
            documentReference.id
        } catch (e: Exception) {
            Log.e(TAG, "Error al guardar la Finanza", e)
            ""
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            gastosCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar la Finanza", e)
            false
        }
    }

    override suspend fun update(finanza: Finanza) {
        try {
            gastosCollection()
                .document(finanza.id)
                .set(finanza)
                .await()

            Log.d(TAG, "Finanza actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar la Finanza", e)
        }
    }
}