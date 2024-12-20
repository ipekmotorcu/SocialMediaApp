package com.cs394.socialmediaapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs394.socialmediaapp.adapter.PostAdapter
import com.cs394.socialmediaapp.Controller.PostController
import com.cs394.socialmediaapp.databinding.FragmentPostListBinding
import com.cs394.socialmediaapp.R

class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        setupRecyclerView() //Ensure RecyclerView

        binding.uploadButton.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_postListFragment_to_newPostFragment)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val postController = PostController()
        val postList = postController.getSamplePosts()

        postAdapter = PostAdapter(postList)
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPosts.adapter = postAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
