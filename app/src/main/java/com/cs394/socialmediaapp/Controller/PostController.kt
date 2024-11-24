package com.cs394.socialmediaapp.Controller

import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.model.Post

class PostController {

    fun getSamplePosts(): List<Post> {
        return listOf(
            Post(
                username = "user1",
                time = System.currentTimeMillis(),
                caption = "This is a sample post",
                likes = 10,
                userid = "user1_id",
                downloadUrl = R.drawable.sahil
            ),
            Post(
                username = "user2",
                time = System.currentTimeMillis(),
                caption = "Another example post",
                likes = 25,
                userid = "user2_id",
                downloadUrl = R.drawable.sahil
            ),
            Post(
                username = "user3",
                time = System.currentTimeMillis(),
                caption = "Loving this new app!",
                likes = 50,
                userid = "user3_id",
                downloadUrl = R.drawable.sahil
            )
        )
    }
}