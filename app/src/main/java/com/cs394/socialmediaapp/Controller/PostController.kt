package com.cs394.socialmediaapp.Controller

import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.Model.Post

class PostController {

    fun getSamplePosts(): List<Post> {
        return listOf(
            Post(
                username = "Emin",
                time = System.currentTimeMillis(),
                caption = "This is a sample post",
                likes = 10,
                userid = "user1_id",
                downloadUrl = R.drawable.book
            ),
            Post(
                username = "Sabina",
                time = System.currentTimeMillis(),
                caption = "Another example post",
                likes = 25,
                userid = "user2_id",
                downloadUrl = R.drawable.paint
            ),
            Post(
                username = "İpek",
                time = System.currentTimeMillis(),
                caption = "Loving this new app!",
                likes = 50,
                userid = "user3_id",
                downloadUrl = R.drawable.sea
            ), Post(
                username = "İrem",
                time = System.currentTimeMillis(),
                caption = "Loving this new app!",
                likes = 150,
                userid = "user4_id",
                downloadUrl = R.drawable.sahil
            )
        )
    }
}