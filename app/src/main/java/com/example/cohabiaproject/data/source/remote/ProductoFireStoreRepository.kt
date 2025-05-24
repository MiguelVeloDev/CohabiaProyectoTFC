package com.example.cohabiaproject.data.source.remote

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cohabiaproject.domain.model.Producto
import com.example.cohabiaproject.domain.model.Sesion
import com.example.cohabiaproject.domain.model.UsoPrograma
import com.example.cohabiaproject.domain.repository.ElectrodomesticoRepository
import com.example.cohabiaproject.domain.repository.ProductoRepository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProductoFirestoreRepository(val firestore: FirebaseFirestore):
    ProductoRepository {

    private fun productosCollection() =
        firestore.collection("casas").document(Sesion.casaId).collection("productos")

    override suspend fun getById(id: String): Producto? {
        return try {
            val documentSnapshot = productosCollection().document(id).get().await()
            documentSnapshot.toObject(Producto::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el Electrodoméstico", e)
            null
        }
    }

    override fun list(): Flow<List<Producto>> {
        return callbackFlow {
            val listener = productosCollection()
                .orderBy("nombre", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    val items = snapshots?.documents?.mapNotNull { doc ->
                        val producto = doc.toObject(Producto::class.java)
                        producto?.copy(id = doc.id)
                    } ?: emptyList()

                    trySend(items)
                }

            awaitClose { listener.remove() }
        }
    }

    override suspend fun save(producto: Producto): Boolean {
        return try {
            productosCollection().add(producto).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun delete(id: String): Boolean {
        return try {
            productosCollection().document(id).delete().await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun update(producto: Producto) {
        try {
            productosCollection()
                .document(producto.id)
                .set(producto)
                .await()

            Log.d(TAG, "Producto actualizada correctamente")
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el electrodoméstico", e)
        }
    }
}