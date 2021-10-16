package com.klima7.services.common.data.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.klima7.services.common.data.converters.toDomain
import com.klima7.services.common.data.entities.CategoryEntity
import com.klima7.services.common.data.entities.ServiceEntity
import com.klima7.services.common.data.utils.getCacheFirst
import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.Outcome
import kotlinx.coroutines.tasks.await

class ServicesRepository(
    private val firestore: FirebaseFirestore
) {

    suspend fun getAllServices(): Outcome<Failure, List<Service>> {
        return try {
            val snapshot = firestore
                .collection("services")
                .get()
                .await()
            val services: List<Service> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(ServiceEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(services)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getAllServices", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getAllCategories(): Outcome<Failure, List<Category>> {
        return try {
            val snapshot = firestore
                .collection("categories")
                .get()
                .await()
            val categories: List<Category> = snapshot.documents.map { document ->
                Pair(document.id, document.toObject(CategoryEntity::class.java))
            }.filter { it.second != null }.map { it.second!!.toDomain(it.first) }
            Outcome.Success(categories)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getAllCategories", e)
            Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getService(serviceId: String): Outcome<Failure, Service> {
        try {
            val snapshot = firestore
                .collection("services")
                .document(serviceId)
                .getCacheFirst()
                .await()
            val serviceEntity = snapshot.toObject(ServiceEntity::class.java)
            val service = serviceEntity?.toDomain(snapshot.id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(service)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getService", e)
            return Outcome.Failure(e.toDomain())
        }
    }

    suspend fun getCategory(categoryId: String): Outcome<Failure, Category> {
        try {
            val snapshot = firestore
                .collection("categories")
                .document(categoryId)
                .getCacheFirst()
                .await()
            val categoryEntity = snapshot.toObject(CategoryEntity::class.java)
            val category = categoryEntity?.toDomain(snapshot.id)
                ?: return Outcome.Failure(Failure.NotFoundFailure)
            return Outcome.Success(category)
        } catch(e: Exception) {
            Log.e("Hello", "Error during getExpert", e)
            return Outcome.Failure(e.toDomain())
        }
    }

}