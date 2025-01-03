package com.cs394.socialmediaapp.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.ViewModel
import com.cs394.socialmediaapp.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {
    private lateinit var binding: FragmentNewPostBinding
    private lateinit var viewModel: ViewModel
    private lateinit var progressDialog: ProgressDialog

    private var imageUserPoster: String = ""
    private var nameUserPoster: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewPostBinding.inflate(inflater, container, false)


        binding.postButton.setOnClickListener {//şimdilik buraya aldım yoksa çalışmıyordu
            // val caption = binding.addCaption.text.toString() //yamulmuyorsam bu sayfadan postliste geçmek lazım
            view?.findNavController()?.navigate(R.id.action_newPostFragment_to_postListFragment)}
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postID = java.util.UUID.randomUUID().toString()

        // Initialize ViewModel
       /* viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        progressDialog = ProgressDialog(requireContext())


        viewModel.name.observe(viewLifecycleOwner) { nameUserPoster = it!! }
        viewModel.image.observe(viewLifecycleOwner) { imageUserPoster = it!! }
*/

        binding.imageToPost.setOnClickListener {
            takeImageInput(postID)
        }

        binding.postButton.setOnClickListener {
           // val caption = binding.addCaption.text.toString() //yamulmuyorsam bu sayfadan postliste geçmek lazım
            view?.findNavController()?.navigate(R.id.action_newPostFragment_to_postListFragment)



            TODO("some Firebase stuff before this line to really store the image...")
        }
    }*/

    private fun takeImageInput(postID: String) {//uploads an image from the gallery.
        TODO("Valla bunu daha öğrenmedim :p")
    }
}
