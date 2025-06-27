package com.relearn.app.feature.http

import android.util.Log

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relearn.app.feature.http.User

import kotlinx.coroutines.launch
import androidx.compose.runtime.State



class ApiViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> = _users

    init {
        viewModelScope.launch {
            try {
                _posts.value = RetrofitInstance.api.getPosts()
                _users.value = RetrofitInstance.api.getUsers()
            } catch (e: Exception) {
                Log.e("ApiViewModel", "Eroare: ${e.message}")
            }
        }
    }
}
