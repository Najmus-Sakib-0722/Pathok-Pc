package com.pathok.app.data.model

import com.google.firebase.firestore.DocumentId

data class Chapter(
    @DocumentId
    val id: String = "",
    val title: String = "",       // না থাকলে খালি স্ট্রিং থাকবে, ক্র্যাশ করবে না
    val content: String = "",
    val order: Long = -1          // -1 মানে "order ফিল্ড ছিল না", app-এ ফলব্যাক সাজানোর জন্য
)