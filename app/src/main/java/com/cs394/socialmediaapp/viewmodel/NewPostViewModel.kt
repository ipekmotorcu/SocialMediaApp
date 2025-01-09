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
import android.graphics.drawable.BitmapDrawable
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
    @SuppressLint("UseCompatLoadingForDrawables") //bu silinebilir artık
    fun convertDrawableToBase64(context: Context?, drawableResId: Int): String? {
        return try {
            // Get the drawable from resources
            val drawable = context?.resources?.getDrawable(drawableResId, null)

            // Convert the drawable to a Bitmap
            val bitmap = (drawable as BitmapDrawable).bitmap

            // Convert the Bitmap to a byte array
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            // Encode the byte array to a Base64 string
            Base64.getEncoder().encodeToString(byteArray)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }







    @RequiresApi(Build.VERSION_CODES.O)
    fun uploadImage(username: String, caption:String, context: Context?) {
        val uri = _imageUri.value
        //
        //val filePath = "/Users/dulumrae/AndroidStudioProjects/ALLAHIM/SocialMediaApp/app/src/main/res/drawable/sahil.jpg"
        //val uri = Uri.fromFile(File(filePath))

        //
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
