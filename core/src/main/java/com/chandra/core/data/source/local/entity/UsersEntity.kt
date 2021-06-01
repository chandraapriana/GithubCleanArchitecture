package com.chandra.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tbl_users")
data class UsersEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "location")
    var location: String?,

    @ColumnInfo(name = "public_repos")
    var publicRepos: Int?,

    @ColumnInfo(name = "followers")
    var followers: Int?,

    @ColumnInfo(name = "following")
    var following: Int?,

    @ColumnInfo(name = "company")
    var company: String?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean?
)
