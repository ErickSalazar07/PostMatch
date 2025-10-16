package com.example.postmatch.data.datasource.impl.firestore

import com.example.postmatch.data.datasource.ReviewRemoteDataSource
import com.example.postmatch.data.dtos.CreateReviewDto
import com.example.postmatch.data.dtos.ReviewDto
import com.example.postmatch.data.dtos.UpdateReviewDto
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReviewFirestoreDataSourceImpl @Inject constructor(private val db: FirebaseFirestore): ReviewRemoteDataSource {
    override suspend fun getAllReviews(): List<ReviewDto> {
        val snapshot = db.collection("reviews").get().await()
        return snapshot.documents.map { doc ->
            val review = doc.toObject(ReviewDto::class.java)
            review?.copy(id = doc.id) ?: throw Exception("Review not found")
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
        TODO("Not yet implemented")
    }

    override suspend fun updateReview(
        id: String,
        review: UpdateReviewDto
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getReviewsByUser(userId: String): List<ReviewDto> {
        TODO("Not yet implemented")
    }

    override suspend fun sendOrDeleteLike(reviewId: String, userId: String) {
        val reviewRef=  db.collection("reviews").document(reviewId)
        val likesRef =  reviewRef.collection("likes").document(userId)

        db.runTransaction { transaction ->


            val likeDoc = transaction.get(likesRef)

            if(likeDoc.exists()){
                transaction.delete(likesRef)
                transaction.update(reviewRef,"likesCount", FieldValue.increment(-1))


            }else{
                transaction.set(likesRef, mapOf("timestamp" to FieldValue.serverTimestamp()))
                transaction.update(reviewRef,"likesCount", FieldValue.increment(1))

            }

        }
    }

}