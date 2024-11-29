package com.cs394.socialmediaapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs394.socialmediaapp.Model.Post
import com.cs394.socialmediaapp.PostClickedActivity
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding.*


class PostAdapter(private val postList: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(val binding: RecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        with(holder.binding) {
            imageViewPost.setImageResource(post.downloadUrl) // Şimdilik böyle
            textViewUsername.text = post.username
            textViewCaption.text = post.caption
            textViewLikes.text = "${post.likes} likes"

            val noOfLikes:String = post.likes.toString()

            imageViewPost.setOnClickListener{
                val context = it.context
                val intent = Intent(context, PostClickedActivity::class.java)
                intent.putExtra("downloadUrl", post.downloadUrl)
                intent.putExtra("username", post.username)
                intent.putExtra("caption", post.caption)
                intent.putExtra("likes", noOfLikes) //burada biraz sorun yaşıyorum nedense
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}