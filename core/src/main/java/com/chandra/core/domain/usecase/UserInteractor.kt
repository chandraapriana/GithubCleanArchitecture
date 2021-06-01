package com.chandra.core.domain.usecase

import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import com.chandra.core.domain.repository.IUsersAppRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor (private val repository: IUsersAppRepository):UserUseCase{
    override fun getUsers(): Flow<Resource<List<Users>>> = repository.getUsers()
    override fun getDetailUser(username: String): Flow<Resource<Users>> = repository.getDetailUser(username)
    override fun getFavoriteUsers(): Flow<List<Users>> =repository.getFavoriteUsers()

    override fun getFavoriteUsersByUname(username: String): Flow<Users>? = repository.getFavoriteUsersByUname(username)

    override suspend fun insertUser(user: Users) = repository.insertUsers(user)
    override suspend fun deleteUser(user: Users): Int =repository.deleteUsers(user)
}