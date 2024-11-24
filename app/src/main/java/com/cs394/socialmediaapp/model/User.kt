package com.cs394.socialmediaapp.Model

data class User(

    val userid: String? = "", val username: String? = "", val image: String? ="",
    val followers: Int? = 0, val following: Int?= 0,
    val name: String = "",
    val email: String = ""
)