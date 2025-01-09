package com.cs394.socialmediaapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cs394.socialmediaapp.Model.Post
import com.cs394.socialmediaapp.PostClickedActivity
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import java.io.File
import java.io.FileOutputStream
import java.util.Base64


class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    class PostViewHolder(val binding: RecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        Log.d("PostAdapter", "CREATED")
        val binding = RecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O) //burada kullanılmıyor ama silmek istemiyorum :p
    fun convertBase64ToImageUri(context: Context?, base64String: String, fileName: String): Uri? {
        return try {
            // Decode the Base64 string into a byte array
            val decodedBytes = Base64.getDecoder().decode(base64String)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            val file = File(context?.cacheDir, fileName)

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
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        Log.d("PostAdapter", "username ${post.username} uri ${post.postUri}")
        with(holder.binding) {
            imageViewPost.setImageURI(post.postUri)

            textViewUsername.text = post.username
            textViewCaption.text = post.caption
            textViewLikes.text = "${post.likes} likes"

            imageViewPost.setOnClickListener {
                val context = it.context
                val intent = Intent(context, PostClickedActivity::class.java)
                intent.putExtra("downloadUrl", post.downloadUrl)
                intent.putExtra("username", post.username)
                intent.putExtra("caption", post.caption)
                intent.putExtra("likes", post.likes.toString())
                intent.putExtra("postUri", post.postUri.toString())
                context.startActivity(intent)
            }
        }
    }

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.userid == newItem.userid
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
}

@androidx.databinding.BindingAdapter("submitList")
fun RecyclerView.submitListAdapter(posts: List<Post>?) {
    (adapter as? PostAdapter)?.submitList(posts)
}
