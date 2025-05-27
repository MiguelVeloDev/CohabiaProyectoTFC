package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Categoria
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.CategoriaRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CategoriaFirestoreRepository(val firestore: FirebaseFirestore):
    CategoriaRepository {

    private fun categoriasCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("categorias")

    override suspend fun getById(id: String): Categoria? {
        return try {
            val documentSnapshot = categoriasCollection().document(id).get().await()
            documentSnapshot.toObject(Categoria::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Categoria>> {
        return callbackFlow {
            val listener = categoriasCollection()
                .orderBy("nombre", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val categoria = doc.toObject(Categoria::class.java)
                        categoria?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(categoria: Categoria): Boolean {
        return try {
            categoriasCollection().add(categoria).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            categoriasCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(categoria: Categoria) {
        try {
            categoriasCollection()
                .document(categoria.id)
                .set(categoria)
                .await()

            Log.d(TAG, "Categoria actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}