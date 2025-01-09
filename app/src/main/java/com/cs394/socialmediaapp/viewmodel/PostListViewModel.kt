package com.cs394.socialmediaapp.viewmodel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.cs394.socialmediaapp.Model.Post
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.File
import java.io.FileOutputStream
import java.util.Base64

class PostListViewModel: ViewModel() {
    private val database = FirebaseDatabase.getInstance("https://socialmediaapp-cc3ad-default-rtdb.europe-west1.firebasedatabase.app/")
    private val ref = database.reference

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertBase64ToImageUri(context: Context?, base64String: String, fileName: String): Uri? {
        return try {
            // Decode the Base64 string into a byte array
            val decodedBytes = Base64.getDecoder().decode(base64String)

            // Decode the byte array into a Bitmap
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            // Create a temporary file to store the image
            val file = File(context?.cacheDir, fileName)

            // Save the Bitmap to the file
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }

            // Return the Uri for the saved image
            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null if there is an error
        }
    }


    fun fetchPostList(context: Context?, onResult: (List<Post>) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val postList = mutableListOf<Post>()
                var i = 0
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        val username = postSnapshot.child("username").getValue(String::class.java)
                        val caption = postSnapshot.child("caption").getValue(String::class.java)
                        val likes = postSnapshot.child("likes").getValue(Int::class.java)
                        val base64String = postSnapshot.child("base64Image").getValue(String::class.java)
                        val postUri = convertBase64ToImageUri(context, base64String.toString(), "photo.jpg$i")
                        postList.add(
                            Post(
                                username = username,
                                time = System.currentTimeMillis(),
                                caption = caption,
                                likes = likes,
                                userid = base64String, // Avoid repurposing here
                                postUri = postUri
                            )
                        )
                        i += 1
                    }
                    onResult(postList) // Return the list through the callback
                } else {
                    onResult(emptyList()) // Return an empty list if no data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "Error reading data: ${error.message}")
                onResult(emptyList()) // Handle cancellation gracefully
            }
        })
    }




    fun fetchPostLis2t(context: Context?): ArrayList<Post> {
        val postList = ArrayList<Post>()
        ref.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                // Check if the data exists
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        // Access each child node
                        val username = postSnapshot.child("username").getValue(String::class.java)
                        val caption = postSnapshot.child("caption").getValue(String::class.java)
                        val likes = postSnapshot.child("likes").getValue(Int::class.java)
                        val base64String = postSnapshot.child("base64Image").getValue(String::class.java)
                        val postUri = convertBase64ToImageUri(context, base64String.toString(),"photo.jpg")
                        postList.add(
                            Post(
                                username = username,
                                time = System.currentTimeMillis(),
                                caption =  caption,
                                likes = likes,
                                userid = base64String, // it is TERRIBLE practice, repurposing like this...
                                postUri = postUri
                            ))
                        // Print or process the data
                        Log.d("FirebaseRepository", "Username: $username, Caption: $caption, Uri: $postUri")
                    }
                } else {
                    Log.d("FirebaseRepository", "No data found in posts")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "Error reading data: ${error.message}")
            }
        })
        return postList
    }
    fun fetchPosts() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Check if the data exists
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) {
                        // Access each child node
                        val username = postSnapshot.child("username").getValue(String::class.java)
                        val caption = postSnapshot.child("caption").getValue(String::class.java)

                        val base64String = postSnapshot.child("base64Image").getValue(String::class.java)

                        // Print or process the data
                        Log.d("FirebaseRepository", "Username: $username, Caption: $caption")
                    }
                } else {
                    Log.d("FirebaseRepository", "No data found in posts")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseRepository", "Error reading data: ${error.message}")
            }
        })
    }
}