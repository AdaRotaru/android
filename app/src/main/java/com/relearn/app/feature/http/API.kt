package com.relearn.app.feature.http

import retrofit2.http.GET
import com.relearn.app.feature.http.User

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("users")
    suspend fun getUsers(): List<User>
}
