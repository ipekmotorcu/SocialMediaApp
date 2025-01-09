package com.cs394.socialmediaapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs394.socialmediaapp.adapter.PostAdapter
import com.cs394.socialmediaapp.databinding.FragmentPostListBinding
import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.viewmodel.PostListViewModel

class PostListFragment : Fragment() {

    private var _binding: FragmentPostListBinding? = null
    private val binding get() = _binding!!
    private lateinit var postAdapter: PostAdapter

    private val viewModel: PostListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostListBinding.inflate(inflater, container, false)
        setupRecyclerView()

        binding.logout.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_postListFragment_to_loginFragment)
        }

        binding.fabUpload.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_postListFragment_to_newPostFragment)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        // Initialize the adapter
        postAdapter = PostAdapter()
        binding.recyclerViewPosts.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPosts.adapter = postAdapter
        // Submit the list to the adapter
        viewModel.fetchPostList(context) { postList ->
            Log.d("PostListFragment", "Fetched posts: $postList")
            postAdapter.submitList(postList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
