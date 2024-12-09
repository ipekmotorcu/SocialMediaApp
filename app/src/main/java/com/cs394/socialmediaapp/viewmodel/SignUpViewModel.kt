package com.cs394.socialmediaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class SignUpStatus(val message: String, val isSuccess: Boolean)

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()//Firebase'i initialize ediyoruz
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _signUpStatus = MutableLiveData<SignUpStatus>()
    val signUpStatus: LiveData<SignUpStatus> get() = _signUpStatus

    fun signUpUser(name: String, email: String, password: String) {
        // Input validation
        if (name.isEmpty()) {
            _signUpStatus.value = SignUpStatus("Name cannot be empty", false)
            return
        }
        if (email.isEmpty()) {
            _signUpStatus.value = SignUpStatus("Email cannot be empty", false)
            return
        }
        if (password.isEmpty()) {
            _signUpStatus.value = SignUpStatus("Password cannot be empty", false)
            return
        }

        // Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Save user information in Firestore
                    val userId = auth.currentUser?.uid ?: ""
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email
                    )
                    db.collection("users").document(userId).set(user)
                        .addOnSuccessListener {
                            _signUpStatus.value = SignUpStatus("Sign Up Successful", true)
                        }
                        .addOnFailureListener {
                            _signUpStatus.value = SignUpStatus("Failed to save user data", false)
                        }
                } else {
                    _signUpStatus.value = SignUpStatus(
                        task.exception?.message ?: "Sign Up Failed", false
                    )
                }
            }
    }
}
