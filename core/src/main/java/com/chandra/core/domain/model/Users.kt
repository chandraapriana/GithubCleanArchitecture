package com.chandra.core.domain.model

data class Users(

    val id: String?,
    val login: String?,
    val avatarUrl: String?,
    val name:String?,
    val location:String?,
    val publicRepos:Int?,
    val followers:Int?,
    val following:Int?,
    val company:String?,
    var isFavorite:Boolean?

)