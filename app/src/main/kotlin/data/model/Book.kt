package com.pathok.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class Book(
    @DocumentId
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val category: String = "",
    val desc: String = "",
    val thumbUrl: String = "",
    val chapterCount: Long = 0,
    val createdBy: String = "",
    val createdAt: Timestamp? = null
)