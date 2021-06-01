package com.chandra.core.domain.repository

import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import kotlinx.coroutines.flow.Flow


interface IUsersAppRepository {
     fun getUsers(): Flow<Resource<List<Users>>>
     fun getDetailUser(username:String): Flow<Resource<Users>>

     fun getFavoriteUsers():Flow<List<Users>>
     fun getFavoriteUsersByUname(username: String):Flow<Users>?
     suspend fun insertUsers(users:Users)
     suspend fun deleteUsers(users:Users):Int
}