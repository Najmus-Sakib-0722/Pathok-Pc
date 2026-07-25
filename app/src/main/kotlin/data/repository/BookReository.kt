package com.pathok.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pathok.app.data.model.Book
import kotlinx.coroutines.tasks.await

class BookRepository {
    private val db = FirebaseFirestore.getInstance()

    // সব বই আনো, সবচেয়ে নতুন আগে
    suspend fun getAllBooks(): List<Book> {
        return try {
            db.collection("contents")
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(Book::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ক্যাটাগরি অনুযায়ী বই আনো (দরকার হলে ব্যবহার করা যাবে)
    suspend fun getBooksByCategory(category: String): List<Book> {
        return try {
            db.collection("contents")
                .whereEqualTo("category", category)
                .get()
                .await()
                .toObjects(Book::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }
}