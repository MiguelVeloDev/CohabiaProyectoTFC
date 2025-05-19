package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Electrodomestico
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ElectrodomesticoFirestoreRepository(val firestore: FirebaseFirestore):
    ElectrodomesticoRepository {

    private val electrodomesticosCollection = firestore.collection("electrodomesticos")


    override suspend fun getById(id: String): Electrodomestico? {
        return try {
            val documentSnapshot = electrodomesticosCollection.document(id).get().await()
            documentSnapshot.toObject(Electrodomestico::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Electrodomestico>> {
        return callbackFlow {
            val listener = electrodomesticosCollection
                .orderBy("nombre", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->

                        val electrodomestico = doc.toObject(Electrodomestico::class.java)
                        electrodomestico?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(electrodomestico: Electrodomestico): Boolean {
        return try {
            electrodomesticosCollection.add(electrodomestico).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(electrodomestico: Electrodomestico): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(electrodomestico: Electrodomestico) {
        try {
            electrodomesticosCollection
                .document(electrodomestico.id)
                .set(electrodomestico)
                .await()

            Log.d(TAG, "Electrodoméstico actualizado correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}