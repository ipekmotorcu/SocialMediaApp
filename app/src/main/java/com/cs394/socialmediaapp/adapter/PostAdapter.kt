package com.cs394.socialmediaapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cs394.socialmediaapp.Model.Post
import com.cs394.socialmediaapp.PostClickedActivity
import com.cs394.socialmediaapp.databinding.RecyclerViewRowBinding

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter


class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    class PostViewHolder(val binding: RecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = RecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        with(holder.binding) {
            imageViewPost.setImageResource(post.downloadUrl) // Temporary placeholder
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
