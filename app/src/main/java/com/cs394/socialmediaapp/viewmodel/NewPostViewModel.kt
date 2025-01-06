package com.cs394.socialmediaapp.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.rpc.Code
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID
//import kotlin.io.encoding.Base64
import java.util.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class NewPostStatus(var message: String, val isSuccess: Boolean) //fikir olarak direkt signup'tan çaldım :p

class NewPostViewModel: ViewModel() {
//class NewPostViewModel(application: Application) : AndroidViewModel(application) {

    //I have chosen Belgium for the database location to decrease latency
    //I'm afraid this was highly unnecessary.
    private val database = FirebaseDatabase.getInstance("https://socialmediaapp-cc3ad-default-rtdb.europe-west1.firebasedatabase.app/")
    private val ref = database.reference

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    private val _newPostStatus = MutableLiveData<NewPostStatus>()
    val newPostStatus: LiveData<NewPostStatus> get() = _newPostStatus

    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?, pickImageRequest: Int) {
        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK) {
            _imageUri.value = data?.data
        }
    }
    fun openGalleryIntent(): Intent{
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        return intent
    }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Recycle") //surely there's a memory issue here
    private fun convertImageToBase64(uri: Uri, context: Context?): String? {
        try {
            val inputStream: InputStream? = context?.contentResolver?.openInputStream(uri) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            return Base64.getEncoder().encodeToString(byteArray)
        }
        catch (e: Exception){
            Log.e("NewPostViewModel", "problem with conversion", e)
            return null
        }
    }

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


    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImage(username: String, caption:String, context: Context?) {
        val uri = _imageUri.value
        if (uri != null) {
            // Convert image to Base64 string
            val base64String = convertImageToBase64(uri, context)
            if (base64String != null) {
                savePostToDatabase(username, base64String, caption)
            } else {
                _newPostStatus.value?.message = "Failed to encode image"
                Log.w("NewPostViewModel", "Base64 encoding failed")
            }
        } else {
            _newPostStatus.value?.message = "No image selected"
            Log.w("NewPostViewModel", "No image to upload")
        }
    }

    private fun savePostToDatabase(username: String, base64Image: String, caption: String) {
        val postId = ref.push().key ?: UUID.randomUUID().toString()
        val post = mapOf(
            "username" to username,
            "base64Image" to base64Image,
            "caption" to caption
        )
        ref.child(postId).setValue(post)
            .addOnSuccessListener {
                _newPostStatus.value?.message = "Upload done" //bu gereksiz ama meh
                Log.d("NewPostViewModel", "Post uploaded: $post")
            }
            .addOnFailureListener{ exception ->
                _newPostStatus.value?.message = "cort"
                Log.e("NewPostViewModel", "Photo upload failed mate", exception)

            }

    }

}
