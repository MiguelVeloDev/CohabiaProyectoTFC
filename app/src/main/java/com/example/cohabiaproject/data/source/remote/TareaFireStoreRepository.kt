package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Finanza
import com.example.cohabiaproject.domain.model.Tarea
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.TareaRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TareaFirestoreRepository(val firestore: FirebaseFirestore):
    TareaRepository {

    private fun tareasCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("tareas")

    override suspend fun getById(id: String): Tarea? {
        return try {
            val documentSnapshot = tareasCollection().document(id).get().await()
            documentSnapshot.toObject(Tarea::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Tarea>> {
        return callbackFlow {
            val listener = tareasCollection()
                .orderBy("contenido", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val tarea = doc.toObject(Tarea::class.java)
                        tarea?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }


    override fun listEsteMes(mes: Int, año: Int): Flow<List<Tarea>> {
        return callbackFlow {
            val listener =  tareasCollection()
                .orderBy("contenido", Query.Direction.DESCENDING)
                .whereEqualTo("mes", mes)
                .whereEqualTo("año", año)
                .whereEqualTo("usuario", Sesion.userId)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val tarea = doc.toObject(Tarea::class.java)
                        tarea?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }
    override suspend fun save(tarea: Tarea): Boolean {
        return try {
            tareasCollection().add(tarea).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            tareasCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(tarea: Tarea) {
        try {
            tareasCollection()
                .document(tarea.id)
                .set(tarea)
                .await()

            Log.d(TAG, "Tarea actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}