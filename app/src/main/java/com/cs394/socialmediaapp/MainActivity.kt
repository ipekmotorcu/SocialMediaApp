package com.cs394.socialmediaapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cs394.socialmediaapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun testFirestoreWrite() {
        val testUser = hashMapOf(
            "name" to "Test User",
            "email" to "test@example.com"
        )
        db.collection("users").document("testUserId").set(testUser)
            .addOnSuccessListener {
                Log.d("FirestoreTest", "Test user saved successfully.")
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreTest", "Failed to save test user: ${exception.message}")
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        testFirestoreWrite()
        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
