package com.cs394.socialmediaapp.Model

data class Post(
    val postId: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val userReference: String = "",
    val timestamp: Long = System.currentTimeMillis()
)