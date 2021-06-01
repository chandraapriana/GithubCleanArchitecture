package com.chandra.core.data.source.local.room

import androidx.room.*
import com.chandra.core.data.source.local.entity.UsersEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface UsersDao {
    @Query("SELECT * from tbl_users ORDER BY login ASC")
    fun getFavoriteUser(): Flow<List<UsersEntity>>

    @Query("SELECT * FROM tbl_users WHERE login = :username")
    fun getFavoriteUserByUname(username: String): Flow<UsersEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UsersEntity?)

    @Delete
    suspend fun deleteUser(user: UsersEntity): Int
}