package com.pathok.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Author(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val bio: String = "",
    val photoUrl: String = "",
    val updatedAt: Timestamp? = null
)