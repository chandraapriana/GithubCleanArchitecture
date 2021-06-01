package com.chandra.core.domain.usecase

import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
 fun getUsers(): Flow<Resource<List<Users>>>
 fun getDetailUser(username: String): Flow<Resource<Users>>

 fun getFavoriteUsers(): Flow<List<Users>>

 fun getFavoriteUsersByUname(username: String): Flow<Users>?

 suspend fun insertUser(user: Users)

 suspend fun deleteUser(user: Users): Int
}
