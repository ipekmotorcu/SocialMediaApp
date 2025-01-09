package com.cs394.socialmediaapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.cs394.socialmediaapp.R
import com.cs394.socialmediaapp.viewmodel.SignUpViewModel
import com.cs394.socialmediaapp.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    // View binding
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButton.setOnClickListener {
            Log.d("SignUpFragment", "Sign Up button clicked")

            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.signUpUser(name, email, password)

        }

        viewModel.signUpStatus.observe(viewLifecycleOwner, Observer { status ->
            Toast.makeText(context, status.message, Toast.LENGTH_SHORT).show()
           // Log.d("SignUpFragment", "Observer triggered: ${status.message}")
            if (status.isSuccess) {
                Log.d("SignUpFragment", "Sign up functions is a success")
                findNavController().navigate(R.id.action_signUpFragment_to_postListFragment2)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
