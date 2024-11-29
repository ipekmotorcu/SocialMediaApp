package com.cs394.socialmediaapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cs394.socialmediaapp.databinding.ActivityPostClickedBinding

class PostClickedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostClickedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostClickedBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        //setContentView(R.layout.activity_post_clicked)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val url = intent.getIntExtra("downloadUrl",-1)
        val username = intent.getStringExtra("username")
        val caption = intent.getStringExtra("caption")
        val likes = intent.getStringExtra("likes")

        binding.imageViewDetail.setImageResource(url)
        binding.textViewUsername.text = "Posted by: "+username
        binding.textViewLikes.text = likes.toString() + " likes!"
        binding.textViewCaption.text = caption



    }
}