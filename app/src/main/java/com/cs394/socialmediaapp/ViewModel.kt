package com.cs394.socialmediaapp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val image = MutableLiveData<String>() //this being a String may be problematic

}