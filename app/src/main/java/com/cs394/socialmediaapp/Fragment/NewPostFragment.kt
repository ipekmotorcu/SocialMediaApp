package com.cs394.socialmediaapp.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.viewmodel.NewPostViewModel
import com.cs394.socialmediaapp.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {
    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!! //bunun da yapısı signup'a benzesin

    private val PICK_IMAGE_REQUEST = 1

    private val viewModel: NewPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewPostBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.openGalleryButton.setOnClickListener{
            val intent = viewModel.openGalleryIntent()
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
        binding.postButton.setOnClickListener {
            val caption: String = binding.addCaption.text.toString()
            viewModel.uploadImage(username = "emin", caption = caption, context)
            view?.findNavController()?.navigate(R.id.action_newPostFragment_to_postListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.handleActivityResult(requestCode, resultCode, data, PICK_IMAGE_REQUEST)
        Log.d("NewPostFragmentTAG", "Image URI is ${viewModel.imageUri}")
        binding.imageToPost.setImageURI(viewModel.imageUri.value)
    }

}
