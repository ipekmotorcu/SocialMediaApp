package com.cs394.socialmediaapp.viewmodel



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

data class LoginStatus(val message: String, val isSuccess: Boolean)

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus> get() = _loginStatus

    fun loginUser(email: String, password: String) {
        //inputlar olması gerektiği gibi mi ona bakıyor
        if (email.isEmpty()) {
            _loginStatus.value = LoginStatus("Email cannot be empty", false)
            return
        }
        if (password.isEmpty()) {
            _loginStatus.value = LoginStatus("Password cannot be empty", false)
            return
        }

        // Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginStatus.value = LoginStatus("Login Successful", true)
                } else {
                    _loginStatus.value = LoginStatus(
                        task.exception?.message ?: "Login Failed", false
                    )
                }
            }
    }
}
