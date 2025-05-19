package com.example.cohabiaproject.data.source.remote

import com.example.cohabiaproject.domain.model.Casa
import com.example.cohabiaproject.domain.repository.CasaRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CasaFirestoreRepository(
    private val firestore: FirebaseFirestore
) : CasaRepository {

    private val auth = FirebaseAuth.getInstance()
    private val casasCollection = firestore.collection("casas")

    override suspend fun getCasaDelUsuario(): Casa? {
        val uid = auth.currentUser?.uid ?: return null
        val documentoCasa = casasCollection
            .whereArrayContains("miembros", uid)
            .get()
            .await()
        return documentoCasa.documents.firstOrNull()?.let {
            Casa(
                id = it.id,
                nombre = it.getString("nombre") ?: "",
                miembros = it.get("miembros") as? List<String> ?: emptyList()
            )
        }
    }

    override suspend fun saveCasa(nombre: String): String {
        val uid = auth.currentUser?.uid ?: return ""

        val data = mapOf(
            "nombre" to nombre,
            "miembros" to listOf(uid)
        )

        val result = casasCollection.add(data).await()
        return result.id
    }

    override suspend fun getById(casaId: String): Casa? {
        val casaSnapshot = casasCollection.document(casaId).get().await()
        return casaSnapshot.toObject(Casa::class.java)
    }

    override suspend fun updateCasa(casa: Casa) {
        val data = mapOf(
            "nombre" to casa.nombre,
            "miembros" to casa.miembros
        )
        casasCollection.document(casa.id).set(data).await()
    }

    override suspend fun deleteCasa(casaId: String) {
        casasCollection.document(casaId).delete().await()
    }

    override suspend fun unirmeCasa(codigoCasa: String) {

        val casa = casasCollection.document(codigoCasa).get().await()
        casa.reference.update("miembros", FieldValue.arrayUnion(auth.currentUser?.uid))

    }
}