package com.cs394.socialmediaapp.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModel we have extended this
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.ViewModel
import com.cs394.socialmediaapp.databinding.FragmentNewPostBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [NewPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewPostFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentNewPostBinding

    private lateinit var viewModel : ViewModel
    private lateinit var progressDialog: ProgressDialog

    private var imageUserPoster: String = ""
    private var nameUserPoster: String = ""

    /*I will just leave the super as the onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_post, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewPostFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewPostFragment().apply {
                arguments = Bundle().apply {
                }
            }
    } //We wont have mult instances of this fragment though...

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postID = java.util.UUID.randomUUID().toString() //generates a random ID for the post, its form is awful btw.
        //interestingly enough, this gives a string which, I suppose, is the norm
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        progressDialog = ProgressDialog(requireContext())

        viewModel.name.observe(viewLifecycleOwner) { nameUserPoster = it!! }

        viewModel.image.observe(viewLifecycleOwner) { imageUserPoster = it!! } //again, this implementation may be not suitable


        binding.imageToPost.setOnClickListener {
            takeImageInput(postID)
        }
        binding.postButton.setOnClickListener{ //imajı yüklemek için tıklanan buton
            var caption = binding.addCaption.toString() //işte captoin'ı girdiden alıyor falan


            view.findNavController().navigate(R.id.action_newPostFragment_to_postListFragment) //yamulmuyorsam bu sayfadan postliste geçmek lazım
            TODO("some Firebase stuff before this line" +
                    "you know, to really store the image...")
        }
    }
    private fun takeImageInput(postID: String) { //uploads an image from the gallery.
        TODO("Valla bunu daha öğrenmedim :p")
    }
}







