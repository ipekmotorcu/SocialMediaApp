package com.cs394.socialmediaapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cs394.socialmediaapp.databinding.ActivityPostClickedBinding

class PostClickedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostClickedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostClickedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
  // Handle the arrow click
        binding.toolbar.setNavigationOnClickListener {
            finish() // Close the activity and go back
        }
        // Handle Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve Intent Data
        val url = intent.getIntExtra("downloadUrl", -1)
        val username = intent.getStringExtra("username")
        val caption = intent.getStringExtra("caption")
        val likes = intent.getStringExtra("likes")
        val uriString = intent.getStringExtra("postUri")
        Log.d("PostClickedActivity", "postUri fetched: $uriString")
        val postUri = Uri.parse(uriString)
        // Populate Views
        //binding.imageViewDetail.setImageResource(url)
        binding.imageViewDetail.setImageURI(postUri)
        Log.d("PostClickedActivity","URI is set")
        binding.textViewUsername.text = "Posted by: $username"
        binding.textViewLikes.text = "$likes likes!"
        binding.textViewCaption.text = caption
    }
}
