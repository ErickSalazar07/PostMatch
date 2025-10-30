package com.example.postmatch.data.datasource.impl.firestore

import com.example.postmatch.data.ReviewInfo
import com.example.postmatch.data.datasource.ReviewRemoteDataSource
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): ReviewRemoteDataSource {
    override suspend fun getAllReviews(): List<ReviewDto> {
        val snapshot = db.collection("reviews").get().await()
        return snapshot.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            val likesSnapshot = doc.reference.collection("likes").get().await()
            val likesCount = likesSnapshot.size()
            review?.copy(id = doc.id, numLikes = likesCount) ?: throw Exception("Review not found")
        }
    }

    override suspend fun getReviewById(id: String): ReviewDto {
        val docRef = db.collection("reviews").document(id)
        val respuesta = docRef.get().await()
        return respuesta.toObject(ReviewDto::class.java) ?: throw Exception("No se pudo obtener la reseña")
    }

    override suspend fun createReview(review: CreateReviewDto) {
        db.collection("reviews").add(review).await() ?: throw Exception("No se pudo crear la reseña")
    }

    override suspend fun deleteReview(id: String) {
        db.collection("reviews").document(id).delete().await() ?: throw Exception("No se pudo eliminar la reseña")
    }

    override suspend fun updateReview(
        id: String,
        review: UpdateReviewDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDto> {
        val snapshot = db.collection("reviews")
            .whereEqualTo("idUsuario", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.let {
                val likesSnapshot = doc.reference.collection("likes").get().await()
                val likesCount = likesSnapshot.size()
                it.copy(id = doc.id, numLikes = likesCount)
            }
        }
    }

    override suspend fun sendOrDeleteLike(reviewId: String, userId: String) {
        val reviewRef = db.collection("reviews").document(reviewId)
        val likesRef = reviewRef.collection("likes").document(userId)

        val result = db.runTransaction { transaction ->
            val likeDoc = transaction.get(likesRef)

            if (likeDoc.exists()) {
                transaction.delete(likesRef)
                transaction.update(reviewRef, "likesCount", FieldValue.increment(-1))
            } else {
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(reviewRef, "likesCount", FieldValue.increment(1))
            }
        }.await() ?: throw Exception("No se pudo completar la transacción de 'like' en Firebase")
    }

    override suspend fun listenReviews(): Flow<List<ReviewDto>> = callbackFlow {
        val listener = db.collection("reviews")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    launch {
                        val reviews = snapshot.documents.mapNotNull { doc ->
                            val review = doc.toObject(ReviewDto::class.java)
                            review?.let {
                                val likesSnapshot = doc.reference.collection("likes").get().await()
                                val likesCount = likesSnapshot.size()
                                it.copy(id = doc.id, numLikes = likesCount)
                            }
                        }
                        trySend(reviews).isSuccess
                    }
                }
            }

        awaitClose { listener.remove() }
    }


}