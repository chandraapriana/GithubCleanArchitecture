package com.chandra.core.data.source.remote.network

import com.chandra.core.BuildConfig

import com.chandra.core.data.source.remote.response.UserResponse

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    companion object {

        private const val AUTH = BuildConfig.API_KEY
    }


    @GET("/users")
    @Headers(AUTH)
    suspend fun getUser(): List<UserResponse>

    @GET("/users/{username}")
    @Headers(AUTH)
    suspend fun getDetailUser(
        @Path("username") username: String
    ): UserResponse


}