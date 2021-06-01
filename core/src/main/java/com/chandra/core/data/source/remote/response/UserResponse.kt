package com.chandra.core.data.source.remote.response

import com.google.gson.annotations.SerializedName



data class UserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
    @SerializedName("public_repos")
    val publicRepos: Int?,
    @SerializedName("repos_url")
    val reposUrl: String?,
)