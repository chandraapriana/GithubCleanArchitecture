package com.chandra.core.data.source.local

import com.chandra.core.data.source.local.entity.UsersEntity
import com.chandra.core.data.source.local.room.UsersDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val usersDao: UsersDao) {
    fun getFavoriteUsers() : Flow<List<UsersEntity>> = usersDao.getFavoriteUser()

    fun getFavoriteUserByUname(username:String):Flow<UsersEntity>? = usersDao.getFavoriteUserByUname(username)

    suspend fun insertUsers(user: UsersEntity) = usersDao.insertUser(user)

    suspend fun deleteUsers(user: UsersEntity) = usersDao.deleteUser(user)
}