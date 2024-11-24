package com.cs394.socialmediaapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs394.socialmediaapp.model.Post
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding.*
import coil.load


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
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}