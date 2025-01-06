package com.cs394.socialmediaapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.cs394.socialmediaapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        //bunlar silinecek, DB'i denemeye çalışıyorum sadece
        val database = FirebaseDatabase.getInstance("https://socialmediaapp-cc3ad-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = database.reference.child("addendum")

        ref.setValue("this should be the second entry").addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MainActivity", "Data added successfully!")
            } else {
                Log.e("MainActivity", "Failed to add data.", task.exception)
            }
        }
        val TAG: String? = "TAGGG"
        val debugText = ref.toString()
        Log.d(TAG, debugText)



        // Initialize View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the NavHostFragment and NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController



    }
}
